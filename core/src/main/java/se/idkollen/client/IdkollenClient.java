package se.idkollen.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import se.idkollen.client.endpoints.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Authenticated HTTP client for the IDkollen REST API.
 *
 * <p>Obtain an instance via {@link IdkollenClientBuilder}. All network calls return
 * {@link CompletableFuture} and are non-blocking.
 */
public final class IdkollenClient {
    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor(r -> {
        var t = new Thread(r, "idkollen-poll");
        t.setDaemon(true);
        return t;
    });

    private final ObjectMapper mapper = new ObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private final String baseUrl;
    private final String credentials;
    private final String userAgent;
    private final OkHttpClient http;

    private IdkollenClient(String baseUrl, String clientId, String clientSecret, String userAgent, OkHttpClient http) {
        this.baseUrl = baseUrl;
        this.credentials = "Basic " + Base64.getEncoder()
            .encodeToString((clientId + ':' + clientSecret)
            .getBytes(StandardCharsets.UTF_8));
        this.userAgent = userAgent;
        this.http = http;
    }

    /** Return the BankID SE endpoint accessor. */
    public BankIdSeEndpoint bankIdSe() {
        return new BankIdSeEndpoint(this);
    }

    /** Return the BankID NO endpoint accessor. */
    public BankIdNoEndpoint bankIdNo() {
        return new BankIdNoEndpoint(this);
    }

    /** Return the Freja eID endpoint accessor. */
    public FrejaEndpoint freja() {
        return new FrejaEndpoint(this);
    }

    /** Return the MitID endpoint accessor. */
    public MitIdEndpoint mitId() {
        return new MitIdEndpoint(this);
    }

    /** Return the Finnish Trust Network (FTN) endpoint accessor. */
    public FtnEndpoint ftn() {
        return new FtnEndpoint(this);
    }

    /** Return the Vipps MobilePay endpoint accessor. */
    public VippsEndpoint vipps() {
        return new VippsEndpoint(this);
    }

    /** Return the document upload/download endpoint accessor. */
    public DocumentEndpoint document() {
        return new DocumentEndpoint(this);
    }

    private String url(String path) {
        return baseUrl + path;
    }

    public <T> CompletableFuture<T> get(String path, Class<T> type) {
        var req = baseRequest(path).get().build();

        return execute(req, type);
    }

    public <T> CompletableFuture<T> post(String path, Object body, Class<T> type) {
        try {
            var json = mapper.writeValueAsString(body);
            var reqBody = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
            var req = baseRequest(path).post(reqBody).build();

            return execute(req, type);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(new IdkollenError("json", 0, e.getMessage()));
        }
    }

    public CompletableFuture<Void> delete(String path) {
        var req = baseRequest(path).delete().build();

        return executeVoid(req);
    }

    public <T> CompletableFuture<T> postMultipart(String path, MultipartBody form, Class<T> type) {
        var req = baseRequest(path).post(form).build();

        return execute(req, type);
    }

    public CompletableFuture<byte[]> getBytes(String path) {
        var req = baseRequest(path).get().build();
        var future = new CompletableFuture<byte[]>();

        http.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                future.completeExceptionally(new IdkollenError("http", 0, e.getMessage()));
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (response) {
                    if (response.isSuccessful()) {
                        var body = response.body();
                        future.complete(body != null ? body.bytes() : new byte[0]);
                    } else {
                        var msg = response.body() != null ? response.body().string() : "";
                        future.completeExceptionally(new IdkollenError("api", response.code(), msg));
                    }
                }
            }
        });

        return future;
    }

    /**
     * Poll {@code statusFn} repeatedly until {@code isPending} returns {@code false} or the timeout elapses.
     *
     * @throws IdkollenError with code {@code "poll_timeout"} if the timeout is exceeded.
     */
    public <T> CompletableFuture<T> poll(Supplier<CompletableFuture<T>> statusFn, Predicate<T> isPending, PollOptions opts) {
        var deadline = System.currentTimeMillis() + opts.timeoutMs();
        var result = new CompletableFuture<T>();

        doPoll(statusFn, isPending, deadline, opts.intervalMs(), result);

        return result;
    }

    private <T> void doPoll(
        Supplier<CompletableFuture<T>> statusFn,
        Predicate<T> isPending,
        long deadline,
        long intervalMs,
        CompletableFuture<T> result
    ) {
        statusFn.get().whenComplete((status, err) -> {
            if (err != null) {
                result.completeExceptionally(err); return;
            }

            if (!isPending.test(status)) {
                result.complete(status); return;
            }

            if (System.currentTimeMillis() >= deadline) {
                result.completeExceptionally(new IdkollenError("poll_timeout", 0, "Poll timed out"));
                return;
            }

            SCHEDULER.schedule(
                () -> doPoll(statusFn, isPending, deadline, intervalMs, result),
                intervalMs,
                TimeUnit.MILLISECONDS
            );
        });
    }

    private Request.Builder baseRequest(String path) {
        return new Request.Builder()
            .url(url(path))
            .header("Authorization", credentials)
            .header("User-Agent", userAgent);
    }

    private <T> CompletableFuture<T> execute(Request req, Class<T> type) {
        var future = new CompletableFuture<T>();

        http.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                future.completeExceptionally(new IdkollenError("http", 0, e.getMessage()));
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (response) {
                    var body = response.body() != null ? response.body().string() : "";

                    if (response.isSuccessful()) {
                        try {
                            future.complete(mapper.readValue(body, type));
                        } catch (RuntimeException e) {
                            future.completeExceptionally(new IdkollenError("json", 0, e.getMessage()));
                        }
                    } else {
                        future.completeExceptionally(new IdkollenError("api", response.code(), body));
                    }
                }
            }
        });

        return future;
    }

    private CompletableFuture<Void> executeVoid(Request req) {
        var future = new CompletableFuture<Void>();

        http.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                future.completeExceptionally(new IdkollenError("http", 0, e.getMessage()));
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (response) {
                    if (response.isSuccessful()) {
                        future.complete(null);
                    } else {
                        var msg = response.body() != null ? response.body().string() : "";

                        future.completeExceptionally(new IdkollenError("api", response.code(), msg));
                    }
                }
            }
        });

        return future;
    }
}

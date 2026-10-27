package com.identisure.client.internal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.identisure.client.IdentisureError;
import com.identisure.client.PollOptions;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.concurrent.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import okhttp3.*;

/**
 * Internal HTTP helper used by endpoint classes. Not part of the public API.
 *
 * <p>The class is {@code public} only so that {@code com.identisure.client} can use it. The
 * package is not exported from the module.
 *
 * @hidden
 */
public final class Transport {
    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor(r -> {
        var t = new Thread(r, "identisure-poll");
        t.setDaemon(true);
        return t;
    });

    private final ObjectMapper mapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private final String baseUrl;
    private final String credentials;
    private final String userAgent;
    private final Duration timeout;
    private final OkHttpClient http;

    public Transport(
            String baseUrl,
            String clientId,
            String clientSecret,
            String userAgent,
            Duration timeout,
            OkHttpClient http) {
        this.baseUrl = baseUrl;
        this.credentials = "Basic "
                + Base64.getEncoder().encodeToString((clientId + ':' + clientSecret).getBytes(StandardCharsets.UTF_8));
        this.userAgent = userAgent;
        this.timeout = timeout;
        this.http = http;
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
            return CompletableFuture.failedFuture(new IdentisureError("json", 0, e.getMessage()));
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

        newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                future.completeExceptionally(new IdentisureError("http", 0, e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) {
                try (response) {
                    if (response.isSuccessful()) {
                        var body = response.body();
                        future.complete(body != null ? body.bytes() : new byte[0]);
                    } else {
                        var msg = response.body() != null ? response.body().string() : "";
                        future.completeExceptionally(new IdentisureError("api", response.code(), msg));
                    }
                } catch (IOException e) {
                    future.completeExceptionally(new IdentisureError("http", 0, e.getMessage()));
                }
            }
        });

        return future;
    }

    public <T> CompletableFuture<T> poll(
            Supplier<CompletableFuture<T>> statusFn, Predicate<T> isPending, PollOptions opts) {
        var deadline = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(opts.timeoutMs());
        var result = new CompletableFuture<T>();
        doPoll(statusFn, isPending, deadline, opts.intervalMs(), result);
        return result;
    }

    private <T> void doPoll(
            Supplier<CompletableFuture<T>> statusFn,
            Predicate<T> isPending,
            long deadline,
            long intervalMs,
            CompletableFuture<T> result) {
        if (result.isDone()) {
            return;
        }

        statusFn.get().whenComplete((status, err) -> {
            if (err != null) {
                result.completeExceptionally(err);
                return;
            }
            if (!isPending.test(status)) {
                result.complete(status);
                return;
            }
            if (System.nanoTime() - deadline >= 0) {
                result.completeExceptionally(new IdentisureError("poll_timeout", 0, "Poll timed out"));
                return;
            }
            SCHEDULER.schedule(
                    () -> doPoll(statusFn, isPending, deadline, intervalMs, result), intervalMs, TimeUnit.MILLISECONDS);
        });
    }

    private Call newCall(Request req) {
        var call = http.newCall(req);
        call.timeout().timeout(timeout.toNanos(), TimeUnit.NANOSECONDS);
        return call;
    }

    private Request.Builder baseRequest(String path) {
        return new Request.Builder()
                .url(baseUrl + path)
                .header("Authorization", credentials)
                .header("User-Agent", userAgent);
    }

    private <T> CompletableFuture<T> execute(Request req, Class<T> type) {
        var future = new CompletableFuture<T>();

        newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                future.completeExceptionally(new IdentisureError("http", 0, e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) {
                try (response) {
                    var body = response.body() != null ? response.body().string() : "";
                    if (response.isSuccessful()) {
                        try {
                            future.complete(mapper.readValue(body, type));
                        } catch (IOException | RuntimeException e) {
                            future.completeExceptionally(new IdentisureError("json", 0, e.getMessage()));
                        }
                    } else {
                        future.completeExceptionally(new IdentisureError("api", response.code(), body));
                    }
                } catch (IOException e) {
                    future.completeExceptionally(new IdentisureError("http", 0, e.getMessage()));
                }
            }
        });

        return future;
    }

    private CompletableFuture<Void> executeVoid(Request req) {
        var future = new CompletableFuture<Void>();

        newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                future.completeExceptionally(new IdentisureError("http", 0, e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) {
                try (response) {
                    if (response.isSuccessful()) {
                        future.complete(null);
                    } else {
                        var msg = response.body() != null ? response.body().string() : "";
                        future.completeExceptionally(new IdentisureError("api", response.code(), msg));
                    }
                } catch (IOException e) {
                    future.completeExceptionally(new IdentisureError("http", 0, e.getMessage()));
                }
            }
        });

        return future;
    }
}

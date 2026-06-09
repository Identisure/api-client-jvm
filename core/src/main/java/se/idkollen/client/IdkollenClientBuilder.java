package se.idkollen.client;

import okhttp3.OkHttpClient;

/**
 * Fluent builder for {@link IdkollenClient}.
 *
 * <p>Defaults to {@link Environment#PRODUCTION} and a freshly constructed
 * {@link OkHttpClient} unless overridden.
 */
public final class IdkollenClientBuilder {
    private static final String DEFAULT_USER_AGENT = "idkollen-client-jvm/0.1.0";

    private final String clientId;
    private final String clientSecret;
    private String baseUrl = Environment.PRODUCTION.baseUrl;
    private String userAgent = DEFAULT_USER_AGENT;
    private OkHttpClient httpClient;

    public IdkollenClientBuilder(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    /** Select a pre-configured environment. */
    public IdkollenClientBuilder environment(Environment env) {
        this.baseUrl = env.baseUrl;
        return this;
    }

    /** Override the API base URL. Takes precedence over {@link #environment(Environment)}. */
    public IdkollenClientBuilder baseUrl(String url) {
        this.baseUrl = url;
        return this;
    }

    /** Override the User-Agent header. */
    public IdkollenClientBuilder userAgent(String ua) {
        this.userAgent = ua;
        return this;
    }

    /** Inject a pre-configured {@link OkHttpClient}. */
    public IdkollenClientBuilder httpClient(OkHttpClient http) {
        this.httpClient = http;
        return this;
    }

    /** Build the configured {@link IdkollenClient}. */
    public IdkollenClient build() {
        var http = httpClient != null ? httpClient : new OkHttpClient();
        return new IdkollenClient(baseUrl, clientId, clientSecret, userAgent, http);
    }
}

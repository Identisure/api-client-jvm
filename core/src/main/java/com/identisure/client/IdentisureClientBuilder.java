package com.identisure.client;

import okhttp3.OkHttpClient;

import java.time.Duration;

/**
 * Fluent builder for {@link IdentisureClient}.
 *
 * <p>Defaults to {@link Environment#PRODUCTION} and a freshly constructed
 * {@link OkHttpClient} unless overridden.
 */
public final class IdentisureClientBuilder {
    private static final String DEFAULT_USER_AGENT = "identisure-client-jvm/0.1.0";

    private final String clientId;
    private final String clientSecret;
    private String baseUrl = Environment.PRODUCTION.baseUrl;
    private String userAgent = DEFAULT_USER_AGENT;
    private Duration timeout = Duration.ofSeconds(30);
    private OkHttpClient httpClient;

    public IdentisureClientBuilder(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    /** Select a pre-configured environment. */
    public IdentisureClientBuilder environment(Environment env) {
        this.baseUrl = env.baseUrl;
        return this;
    }

    /** Override the API base URL. Takes precedence over {@link #environment(Environment)}. */
    public IdentisureClientBuilder baseUrl(String url) {
        this.baseUrl = url.replaceAll("/+$", "");
        return this;
    }

    /** Override the User-Agent header. */
    public IdentisureClientBuilder userAgent(String ua) {
        this.userAgent = ua;
        return this;
    }

    /** Timeout for each HTTP request, including reading the response body. Must be positive. Default: 30 s. */
    public IdentisureClientBuilder timeout(Duration timeout) {
        this.timeout = timeout;
        return this;
    }

    /** Inject a pre-configured {@link OkHttpClient}. */
    public IdentisureClientBuilder httpClient(OkHttpClient http) {
        this.httpClient = http;
        return this;
    }

    /** Build the configured {@link IdentisureClient}. */
    public IdentisureClient build() {
        var http = httpClient != null ? httpClient : new OkHttpClient();
        return new IdentisureClient(baseUrl, clientId, clientSecret, userAgent, timeout, http);
    }
}

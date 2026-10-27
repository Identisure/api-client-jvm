package com.identisure.client;

/** Pre-configured API base URL selection. */
public enum Environment {
    /** Production environment: {@code https://api.identisure.com}. */
    PRODUCTION("https://api.identisure.com"),
    /** Staging environment: {@code https://stgapi.identisure.com}. */
    STAGING("https://stgapi.identisure.com");

    public final String baseUrl;

    Environment(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}

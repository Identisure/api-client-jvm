package se.idkollen.client;

/** Pre-configured API base URL selection. */
public enum Environment {
    /** Production environment: {@code https://api.idkollen.se}. */
    PRODUCTION("https://api.idkollen.se"),
    /** Staging environment: {@code https://stgapi.idkollen.se}. */
    STAGING("https://stgapi.idkollen.se");

    public final String baseUrl;

    Environment(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}

package se.idkollen.client;

/** Exception thrown by all client operations when a request cannot be completed. */
public class IdkollenError extends RuntimeException {
    /** Short machine-readable error code (e.g. {@code "api"}, {@code "http"}, {@code "json"}, {@code "poll_timeout"}). */
    public final String code;
    /** HTTP status code returned by the server, or {@code 0} for non-HTTP errors. */
    public final int status;

    public IdkollenError(String code, int status, String message) {
        super(message);
        this.code = code;
        this.status = status;
    }
}

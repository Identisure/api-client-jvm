package se.idkollen.client.models;

/** Standardised error code present in all {@code FAILED} status responses. */
public enum ApiErrorCode {
    AUTH_FAILED,
    CANCELLED,
    INVALID_ID,
    CONFLICT,
    INTERNAL_ERROR,
    SESSION_TIMEOUT,
    UNSUPPORTED_CLIENT,
}

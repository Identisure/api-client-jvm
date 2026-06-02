package se.idkollen.client.models;

import org.jspecify.annotations.Nullable;

/**
 * Request body for starting an age verification session.
 *
 * <p>At least one of {@code minAge} or {@code maxAge} must be set.
 */
public class AgeVerificationRequest {
    public @Nullable Integer minAge;
    public @Nullable Integer maxAge;
    public @Nullable String refId;
    public @Nullable String callbackUrl;
    public @Nullable String redirectUrl;

    public AgeVerificationRequest setMinAge(@Nullable Integer v) {
        minAge = v;
        return this;
    }

    public AgeVerificationRequest setMaxAge(@Nullable Integer v) {
        maxAge = v;
        return this;
    }

    public AgeVerificationRequest setRefId(@Nullable String v) {
        refId = v;
        return this;
    }

    public AgeVerificationRequest setCallbackUrl(@Nullable String v) {
        callbackUrl = v;
        return this;
    }

    public AgeVerificationRequest setRedirectUrl(@Nullable String v) {
        redirectUrl = v;
        return this;
    }
}

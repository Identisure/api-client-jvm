package se.idkollen.client.models;

import org.jspecify.annotations.Nullable;

/**
 * Request body for starting a Finnish Trust Network (FTN) authentication session.
 */
public class FtnAuthRequest {
    public @Nullable String redirectUrl;
    public @Nullable Boolean requestPhone;
    public @Nullable Boolean requestEmail;
    public @Nullable Boolean requestAddress;
    public @Nullable String refId;

    public FtnAuthRequest setRedirectUrl(@Nullable String v) {
        redirectUrl = v;
        return this;
    }

    public FtnAuthRequest setRequestPhone(@Nullable Boolean v) {
        requestPhone = v;
        return this;
    }

    public FtnAuthRequest setRequestEmail(@Nullable Boolean v) {
        requestEmail = v;
        return this;
    }

    public FtnAuthRequest setRequestAddress(@Nullable Boolean v) {
        requestAddress = v;
        return this;
    }

    public FtnAuthRequest setRefId(@Nullable String v) {
        refId = v;
        return this;
    }
}

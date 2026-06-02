package se.idkollen.client.models;

import org.jspecify.annotations.Nullable;

/**
 * Request body for starting a MitID authentication session.
 */
public class MitIdAuthRequest {
    public @Nullable String redirectUrl;
    /**
     * Text shown to the user during authentication. Must not contain {@code %} or {@code <} (max 130 chars).
     */
    public @Nullable String referenceText;
    public @Nullable Boolean requestPhone;
    public @Nullable Boolean requestEmail;
    public @Nullable Boolean requestAddress;
    public @Nullable String refId;

    public MitIdAuthRequest setRedirectUrl(@Nullable String v) {
        redirectUrl = v;
        return this;
    }

    public MitIdAuthRequest setReferenceText(@Nullable String v) {
        referenceText = v;
        return this;
    }

    public MitIdAuthRequest setRequestPhone(@Nullable Boolean v) {
        requestPhone = v;
        return this;
    }

    public MitIdAuthRequest setRequestEmail(@Nullable Boolean v) {
        requestEmail = v;
        return this;
    }

    public MitIdAuthRequest setRequestAddress(@Nullable Boolean v) {
        requestAddress = v;
        return this;
    }

    public MitIdAuthRequest setRefId(@Nullable String v) {
        refId = v;
        return this;
    }
}

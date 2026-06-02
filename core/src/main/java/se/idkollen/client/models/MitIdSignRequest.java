package se.idkollen.client.models;

import org.jspecify.annotations.Nullable;

/**
 * Request body for starting a MitID signing session.
 */
public class MitIdSignRequest {
    public String text = "";
    public @Nullable String redirectUrl;
    public @Nullable String refId;

    public MitIdSignRequest setText(String v) {
        text = v;
        return this;
    }

    public MitIdSignRequest setRedirectUrl(@Nullable String v) {
        redirectUrl = v;
        return this;
    }

    public MitIdSignRequest setRefId(@Nullable String v) {
        refId = v;
        return this;
    }
}

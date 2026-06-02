package se.idkollen.client.models;

import org.jspecify.annotations.Nullable;

/**
 * Request body for starting a Vipps MobilePay authentication session.
 */
public class VippsAuthRequest {
    public @Nullable String redirectUrl;
    public @Nullable Boolean requestSsn;
    public @Nullable Boolean requestPhone;
    public @Nullable Boolean requestEmail;
    public @Nullable Boolean requestAddress;
    public @Nullable String refId;
    public @Nullable String appCallbackUri;

    public VippsAuthRequest setRedirectUrl(@Nullable String v) {
        redirectUrl = v;
        return this;
    }

    public VippsAuthRequest setRequestSsn(@Nullable Boolean v) {
        requestSsn = v;
        return this;
    }

    public VippsAuthRequest setRequestPhone(@Nullable Boolean v) {
        requestPhone = v;
        return this;
    }

    public VippsAuthRequest setRequestEmail(@Nullable Boolean v) {
        requestEmail = v;
        return this;
    }

    public VippsAuthRequest setRequestAddress(@Nullable Boolean v) {
        requestAddress = v;
        return this;
    }

    public VippsAuthRequest setRefId(@Nullable String v) {
        refId = v;
        return this;
    }

    public VippsAuthRequest setAppCallbackUri(@Nullable String v) {
        appCallbackUri = v;
        return this;
    }
}

package se.idkollen.client.models;

import org.jspecify.annotations.Nullable;

/**
 * Request body for starting a Vipps MobilePay backchannel authentication session.
 */
public class VippsBackchannelAuthRequest {
    public String phone = "";
    public @Nullable Boolean requestSsn;
    public @Nullable Boolean requestEmail;
    public @Nullable Boolean requestAddress;
    public @Nullable String callbackUrl;
    public @Nullable String refId;

    public VippsBackchannelAuthRequest setPhone(String v) {
        phone = v;
        return this;
    }

    public VippsBackchannelAuthRequest setRequestSsn(@Nullable Boolean v) {
        requestSsn = v;
        return this;
    }

    public VippsBackchannelAuthRequest setRequestEmail(@Nullable Boolean v) {
        requestEmail = v;
        return this;
    }
    public VippsBackchannelAuthRequest setRequestAddress(@Nullable Boolean v) {
        requestAddress = v;
        return this;
    }

    public VippsBackchannelAuthRequest setCallbackUrl(@Nullable String v) {
        callbackUrl = v;
        return this;
    }

    public VippsBackchannelAuthRequest setRefId(@Nullable String v) {
        refId = v;
        return this;
    }
}

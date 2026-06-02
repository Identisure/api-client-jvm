package se.idkollen.client.models;

import org.jspecify.annotations.Nullable;

/**
 * Request body for starting a BankID NO backchannel authentication session.
 */
public class BankIdNoBackchannelAuthRequest {
    public String ssn = "";
    public @Nullable String callbackUrl;
    public @Nullable String refId;

    public BankIdNoBackchannelAuthRequest setSsn(String v) {
        ssn = v;
        return this;
    }

    public BankIdNoBackchannelAuthRequest setCallbackUrl(@Nullable String v) {
        callbackUrl = v;
        return this;
    }

    public BankIdNoBackchannelAuthRequest setRefId(@Nullable String v) {
        refId = v;
        return this;
    }
}

package se.idkollen.client.models;

import org.jspecify.annotations.Nullable;

/**
 * Request body for starting a BankID NO authentication session.
 */
public class BankIdNoAuthRequest {
    public @Nullable String redirectUrl;
    /**
     * Request the user's Norwegian personal number (fødselsnummer).
     */
    public @Nullable Boolean requestSsn;
    public @Nullable Boolean requestPhone;
    public @Nullable Boolean requestEmail;
    public @Nullable Boolean requestAddress;
    public @Nullable String refId;
    public @Nullable String appCallbackUri;

    public BankIdNoAuthRequest setRedirectUrl(@Nullable String v) {
        redirectUrl = v;
        return this;
    }

    public BankIdNoAuthRequest setRequestSsn(@Nullable Boolean v) {
        requestSsn = v;
        return this;
    }

    public BankIdNoAuthRequest setRequestPhone(@Nullable Boolean v) {
        requestPhone = v;
        return this;
    }

    public BankIdNoAuthRequest setRequestEmail(@Nullable Boolean v) {
        requestEmail = v;
        return this;
    }

    public BankIdNoAuthRequest setRequestAddress(@Nullable Boolean v) {
        requestAddress = v;
        return this;
    }

    public BankIdNoAuthRequest setRefId(@Nullable String v) {
        refId = v;
        return this;
    }

    public BankIdNoAuthRequest setAppCallbackUri(@Nullable String v) {
        appCallbackUri = v;
        return this;
    }
}

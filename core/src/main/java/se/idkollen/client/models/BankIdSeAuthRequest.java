package se.idkollen.client.models;

import org.jspecify.annotations.Nullable;

/**
 * Request body for starting a BankID SE authentication session.
 */
public class BankIdSeAuthRequest {
    /**
     * Swedish personal identification number. Restricts the session to this user.
     */
    public @Nullable String ssn;
    /**
     * End-user IP address (or the closest proxy address).
     */
    public @Nullable String ipAddress;
    /**
     * URL to receive the result callback on success or failure.
     */
    public @Nullable String callbackUrl;
    /**
     * Force PIN entry even when biometrics are enabled.
     */
    public @Nullable Boolean pinRequired;
    /**
     * Text describing the purpose of the identification, shown to the user.
     */
    public @Nullable String intent;
    /**
     * Swedish organisation number — enables company signatory check.
     */
    public @Nullable String orgNumber;
    /**
     * Fetch the user's registered address on completion.
     */
    public @Nullable Boolean requestAddress;
    /**
     * Reference ID returned verbatim in the result and callback.
     */
    public @Nullable String refId;

    public BankIdSeAuthRequest setSsn(@Nullable String v) {
        ssn = v;
        return this;
    }

    public BankIdSeAuthRequest setIpAddress(@Nullable String v) {
        ipAddress = v;
        return this;
    }

    public BankIdSeAuthRequest setCallbackUrl(@Nullable String v) {
        callbackUrl = v;
        return this;
    }

    public BankIdSeAuthRequest setPinRequired(@Nullable Boolean v) {
        pinRequired = v;
        return this;
    }

    public BankIdSeAuthRequest setIntent(@Nullable String v) {
        intent = v;
        return this;
    }

    public BankIdSeAuthRequest setOrgNumber(@Nullable String v) {
        orgNumber = v;
        return this;
    }

    public BankIdSeAuthRequest setRequestAddress(@Nullable Boolean v) {
        requestAddress = v;
        return this;
    }

    public BankIdSeAuthRequest setRefId(@Nullable String v) {
        refId = v;
        return this;
    }
}

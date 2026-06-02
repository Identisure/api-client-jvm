package se.idkollen.client.models;

import org.jspecify.annotations.Nullable;

/**
 * Request body for starting a BankID SE signing session.
 */
public class BankIdSeSignRequest {
    /**
     * Visible text the user must approve in the BankID app (max 50 000 chars).
     */
    public String text = "";
    public @Nullable String ssn;
    public @Nullable String ipAddress;
    public @Nullable String callbackUrl;
    public @Nullable Boolean pinRequired;
    /**
     * Hash digest of an associated file.
     */
    public @Nullable String digest;
    public @Nullable String orgNumber;
    public @Nullable Boolean requestAddress;
    public @Nullable String refId;

    public BankIdSeSignRequest setText(String v) {
        text = v;
        return this;
    }

    public BankIdSeSignRequest setSsn(@Nullable String v) {
        ssn = v;
        return this;
    }

    public BankIdSeSignRequest setIpAddress(@Nullable String v) {
        ipAddress = v;
        return this;
    }

    public BankIdSeSignRequest setCallbackUrl(@Nullable String v) {
        callbackUrl = v;
        return this;
    }

    public BankIdSeSignRequest setPinRequired(@Nullable Boolean v) {
        pinRequired = v;
        return this;
    }

    public BankIdSeSignRequest setDigest(@Nullable String v) {
        digest = v;
        return this;
    }

    public BankIdSeSignRequest setOrgNumber(@Nullable String v) {
        orgNumber = v;
        return this;
    }

    public BankIdSeSignRequest setRequestAddress(@Nullable Boolean v) {
        requestAddress = v;
        return this;
    }

    public BankIdSeSignRequest setRefId(@Nullable String v) {
        refId = v;
        return this;
    }
}

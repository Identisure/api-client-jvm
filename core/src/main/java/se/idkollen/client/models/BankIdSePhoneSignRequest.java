package se.idkollen.client.models;

import org.jspecify.annotations.Nullable;

/**
 * Request body for starting a BankID SE phone signing session.
 */
public class BankIdSePhoneSignRequest {
    public String ssn = "";
    public CallInitiator callInitiator = CallInitiator.USER;
    public String text = "";
    public @Nullable String callbackUrl;
    public @Nullable Boolean pinRequired;
    public @Nullable String digest;
    public @Nullable String orgNumber;
    public @Nullable Boolean requestAddress;
    public @Nullable String refId;

    public BankIdSePhoneSignRequest setSsn(String v) {
        ssn = v;
        return this;
    }

    public BankIdSePhoneSignRequest setCallInitiator(CallInitiator v) {
        callInitiator = v;
        return this;
    }

    public BankIdSePhoneSignRequest setText(String v) {
        text = v;
        return this;
    }

    public BankIdSePhoneSignRequest setCallbackUrl(@Nullable String v) {
        callbackUrl = v;
        return this;
    }

    public BankIdSePhoneSignRequest setPinRequired(@Nullable Boolean v) {
        pinRequired = v;
        return this;
    }

    public BankIdSePhoneSignRequest setDigest(@Nullable String v) {
        digest = v;
        return this;
    }

    public BankIdSePhoneSignRequest setOrgNumber(@Nullable String v) {
        orgNumber = v;
        return this;
    }

    public BankIdSePhoneSignRequest setRequestAddress(@Nullable Boolean v) {
        requestAddress = v;
        return this;
    }

    public BankIdSePhoneSignRequest setRefId(@Nullable String v) {
        refId = v;
        return this;
    }
}

package se.idkollen.client.models;

import org.jspecify.annotations.Nullable;

/**
 * Request body for starting a BankID SE phone authentication session.
 */
public class BankIdSePhoneAuthRequest {
    public String ssn = "";
    public CallInitiator callInitiator = CallInitiator.USER;
    public @Nullable String callbackUrl;
    public @Nullable Boolean pinRequired;
    public @Nullable String intent;
    public @Nullable String orgNumber;
    public @Nullable Boolean requestAddress;
    public @Nullable String refId;

    public BankIdSePhoneAuthRequest setSsn(String v) {
        ssn = v;
        return this;
    }

    public BankIdSePhoneAuthRequest setCallInitiator(CallInitiator v) {
        callInitiator = v;
        return this;
    }

    public BankIdSePhoneAuthRequest setCallbackUrl(@Nullable String v) {
        callbackUrl = v;
        return this;
    }

    public BankIdSePhoneAuthRequest setPinRequired(@Nullable Boolean v) {
        pinRequired = v;
        return this;
    }

    public BankIdSePhoneAuthRequest setIntent(@Nullable String v) {
        intent = v;
        return this;
    }

    public BankIdSePhoneAuthRequest setOrgNumber(@Nullable String v) {
        orgNumber = v;
        return this;
    }

    public BankIdSePhoneAuthRequest setRequestAddress(@Nullable Boolean v) {
        requestAddress = v;
        return this;
    }

    public BankIdSePhoneAuthRequest setRefId(@Nullable String v) {
        refId = v;
        return this;
    }
}

package se.idkollen.client.models;

import org.jspecify.annotations.Nullable;

/**
 * Request body for starting a Freja eID signing session.
 */
public class FrejaSignRequest {
    public String text = "";
    public @Nullable String ssn;
    public @Nullable String callbackUrl;
    public @Nullable FrejaRegistrationLevel minRegistrationLevel;
    public @Nullable String orgNumber;
    public @Nullable Boolean requestAddress;
    public @Nullable String refId;

    public FrejaSignRequest setText(String v) {
        text = v;
        return this;
    }

    public FrejaSignRequest setSsn(@Nullable String v) {
        ssn = v;
        return this;
    }

    public FrejaSignRequest setCallbackUrl(@Nullable String v) {
        callbackUrl = v;
        return this;
    }

    public FrejaSignRequest setMinRegistrationLevel(@Nullable FrejaRegistrationLevel v) {
        minRegistrationLevel = v;
        return this;
    }

    public FrejaSignRequest setOrgNumber(@Nullable String v) {
        orgNumber = v;
        return this;
    }

    public FrejaSignRequest setRequestAddress(@Nullable Boolean v) {
        requestAddress = v;
        return this;
    }

    public FrejaSignRequest setRefId(@Nullable String v) {
        refId = v;
        return this;
    }
}

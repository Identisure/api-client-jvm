package se.idkollen.client.models;

import org.jspecify.annotations.Nullable;

/**
 * Request body for starting a Freja eID authentication session.
 */
public class FrejaAuthRequest {
    public @Nullable String ssn;
    public @Nullable String callbackUrl;
    public @Nullable FrejaRegistrationLevel minRegistrationLevel;
    public @Nullable String orgNumber;
    public @Nullable Boolean requestAddress;
    public @Nullable String refId;

    public FrejaAuthRequest setSsn(@Nullable String v) {
        ssn = v;
        return this;
    }

    public FrejaAuthRequest setCallbackUrl(@Nullable String v) {
        callbackUrl = v;
        return this;
    }

    public FrejaAuthRequest setMinRegistrationLevel(@Nullable FrejaRegistrationLevel v) {
        minRegistrationLevel = v;
        return this;
    }

    public FrejaAuthRequest setOrgNumber(@Nullable String v) {
        orgNumber = v;
        return this;
    }

    public FrejaAuthRequest setRequestAddress(@Nullable Boolean v) {
        requestAddress = v;
        return this;
    }

    public FrejaAuthRequest setRefId(@Nullable String v) {
        refId = v;
        return this;
    }
}

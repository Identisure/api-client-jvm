package se.idkollen.client.models;

import org.jspecify.annotations.Nullable;

/**
 * Request body for starting a Freja eID backchannel authentication session.
 */
public class FrejaBackchannelAuthRequest {
    public String ssn = "";
    public String country = "";
    public @Nullable String callbackUrl;
    public @Nullable FrejaRegistrationLevel minRegistrationLevel;
    public @Nullable String orgNumber;
    public @Nullable Boolean requestAddress;
    public @Nullable String refId;

    public FrejaBackchannelAuthRequest setSsn(String v) {
        ssn = v;
        return this;
    }

    public FrejaBackchannelAuthRequest setCountry(String v) {
        country = v;
        return this;
    }

    public FrejaBackchannelAuthRequest setCallbackUrl(@Nullable String v) {
        callbackUrl = v;
        return this;
    }

    public FrejaBackchannelAuthRequest setMinRegistrationLevel(@Nullable FrejaRegistrationLevel v) {
        minRegistrationLevel = v;
        return this;
    }

    public FrejaBackchannelAuthRequest setOrgNumber(@Nullable String v) {
        orgNumber = v;
        return this;
    }

    public FrejaBackchannelAuthRequest setRequestAddress(@Nullable Boolean v) {
        requestAddress = v;
        return this;
    }

    public FrejaBackchannelAuthRequest setRefId(@Nullable String v) {
        refId = v;
        return this;
    }
}

package se.idkollen.client.models;

import org.jspecify.annotations.Nullable;

/**
 * Request body for starting a Freja eID backchannel signing session.
 */
public class FrejaBackchannelSignRequest {
    public String ssn = "";
    public String country = "";
    public String text = "";
    public @Nullable String callbackUrl;
    public @Nullable FrejaRegistrationLevel minRegistrationLevel;
    public @Nullable String orgNumber;
    public @Nullable Boolean requestAddress;
    public @Nullable String refId;

    public FrejaBackchannelSignRequest setSsn(String v) {
        ssn = v;
        return this;
    }

    public FrejaBackchannelSignRequest setCountry(String v) {
        country = v;
        return this;
    }

    public FrejaBackchannelSignRequest setText(String v) {
        text = v;
        return this;
    }

    public FrejaBackchannelSignRequest setCallbackUrl(@Nullable String v) {
        callbackUrl = v;
        return this;
    }

    public FrejaBackchannelSignRequest setMinRegistrationLevel(@Nullable FrejaRegistrationLevel v) {
        minRegistrationLevel = v;
        return this;
    }

    public FrejaBackchannelSignRequest setOrgNumber(@Nullable String v) {
        orgNumber = v;
        return this;
    }

    public FrejaBackchannelSignRequest setRequestAddress(@Nullable Boolean v) {
        requestAddress = v;
        return this;
    }

    public FrejaBackchannelSignRequest setRefId(@Nullable String v) {
        refId = v;
        return this;
    }
}

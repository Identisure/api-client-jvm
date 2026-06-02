package se.idkollen.client.models;

import org.jspecify.annotations.Nullable;

/**
 * Request body for starting a MitID backchannel authentication session.
 */
public class MitIdBackchannelAuthRequest {
    public String ssn = "";
    public String bindingMessage = "";
    public @Nullable String callbackUrl;
    public @Nullable String refId;

    public MitIdBackchannelAuthRequest setSsn(String v) {
        ssn = v;
        return this;
    }

    public MitIdBackchannelAuthRequest setBindingMessage(String v) {
        bindingMessage = v;
        return this;
    }

    public MitIdBackchannelAuthRequest setCallbackUrl(@Nullable String v) {
        callbackUrl = v;
        return this;
    }

    public MitIdBackchannelAuthRequest setRefId(@Nullable String v) {
        refId = v;
        return this;
    }
}

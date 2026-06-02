package se.idkollen.client.endpoints;

import se.idkollen.client.IdkollenClient;
import se.idkollen.client.PollOptions;
import se.idkollen.client.models.*;

import java.util.concurrent.CompletableFuture;

/** Endpoint for Vipps MobilePay operations. */
public class VippsEndpoint {
    private final IdkollenClient client;

    public VippsEndpoint(IdkollenClient client) {
        this.client = client;
    }

    /** Start a Vipps MobilePay authentication session. */
    public CompletableFuture<VippsStatus> authAsync(VippsAuthRequest req) {
        return client.post("/v3/vipps/auth", req, VippsStatus.class);
    }

    /** Start a Vipps MobilePay backchannel authentication session. */
    public CompletableFuture<VippsStatus> backchannelAuthAsync(VippsBackchannelAuthRequest req) {
        return client.post("/v3/vipps/backchannel/auth", req, VippsStatus.class);
    }

    /** Poll the current status of a Vipps MobilePay authentication session. */
    public CompletableFuture<VippsStatus> authStatusAsync(String id) {
        return client.get("/v3/vipps/auth/" + id, VippsStatus.class);
    }

    /** Cancel a Vipps MobilePay authentication session. */
    public CompletableFuture<Void> cancelAuthAsync(String id) {
        return client.delete("/v3/vipps/auth/" + id);
    }

    /** Poll until the authentication session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<VippsStatus> waitForAuthAsync(String id, PollOptions opts) {
        return client.poll(() -> authStatusAsync(id), VippsStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<VippsStatus> waitForAuthAsync(String id) {
        return waitForAuthAsync(id, new PollOptions());
    }
}

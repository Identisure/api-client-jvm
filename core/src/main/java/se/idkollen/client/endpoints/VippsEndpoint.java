package se.idkollen.client.endpoints;

import se.idkollen.client.PollOptions;
import se.idkollen.client.internal.Transport;

import se.idkollen.client.models.*;

import java.util.concurrent.CompletableFuture;

/** Endpoint for Vipps MobilePay operations. */
public class VippsEndpoint {
    private final Transport transport;

    public VippsEndpoint(Transport transport) {
        this.transport = transport;
    }

    /** Start a Vipps MobilePay authentication session. */
    public CompletableFuture<VippsStatus> auth(VippsAuthRequest req) {
        return transport.post("/v3/vipps/auth", req, VippsStatus.class);
    }

    /** Start a Vipps MobilePay backchannel authentication session. */
    public CompletableFuture<VippsStatus> backchannelAuth(VippsBackchannelAuthRequest req) {
        return transport.post("/v3/vipps/backchannel/auth", req, VippsStatus.class);
    }

    /** Poll the current status of a Vipps MobilePay authentication session. */
    public CompletableFuture<VippsStatus> authStatus(String id) {
        return transport.get("/v3/vipps/auth/" + id, VippsStatus.class);
    }

    /** Cancel a Vipps MobilePay authentication session. */
    public CompletableFuture<Void> cancelAuth(String id) {
        return transport.delete("/v3/vipps/auth/" + id);
    }

    /** Poll until the authentication session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<VippsStatus> waitForAuth(String id, PollOptions opts) {
        return transport.poll(() -> authStatus(id), VippsStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<VippsStatus> waitForAuth(String id) {
        return waitForAuth(id, new PollOptions());
    }
}

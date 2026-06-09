package se.idkollen.client.endpoints;

import se.idkollen.client.PollOptions;
import se.idkollen.client.internal.Transport;

import se.idkollen.client.models.*;

import java.util.concurrent.CompletableFuture;

/** Endpoint for Freja eID operations. */
public class FrejaEndpoint {
    private final Transport transport;

    public FrejaEndpoint(Transport transport) {
        this.transport = transport;
    }

    /** Start a Freja eID authentication session. */
    public CompletableFuture<FrejaStatus> auth(FrejaAuthRequest req) {
        return transport.post("/v3/freja/auth", req, FrejaStatus.class);
    }

    /** Start a Freja eID backchannel authentication session. */
    public CompletableFuture<FrejaStatus> backchannelAuth(FrejaBackchannelAuthRequest req) {
        return transport.post("/v3/freja/backchannel/auth", req, FrejaStatus.class);
    }

    /** Start a Freja eID signing session. */
    public CompletableFuture<FrejaStatus> sign(FrejaSignRequest req) {
        return transport.post("/v3/freja/sign", req, FrejaStatus.class);
    }

    /** Start a Freja eID backchannel signing session. */
    public CompletableFuture<FrejaStatus> backchannelSign(FrejaBackchannelSignRequest req) {
        return transport.post("/v3/freja/backchannel/sign", req, FrejaStatus.class);
    }

    /** Start a Freja eID age verification session. */
    public CompletableFuture<AgeVerificationStatus> ageVerification(AgeVerificationRequest req) {
        return transport.post("/v3/freja/age-verification", req, AgeVerificationStatus.class);
    }

    /** Poll the current status of a Freja eID authentication session. */
    public CompletableFuture<FrejaStatus> authStatus(String id) {
        return transport.get("/v3/freja/auth/" + id, FrejaStatus.class);
    }

    /** Poll the current status of a Freja eID signing session. */
    public CompletableFuture<FrejaStatus> signStatus(String id) {
        return transport.get("/v3/freja/sign/" + id, FrejaStatus.class);
    }

    /** Poll the current status of a Freja eID age verification session. */
    public CompletableFuture<AgeVerificationStatus> ageVerificationStatus(String id) {
        return transport.get("/v3/freja/age-verification/" + id, AgeVerificationStatus.class);
    }

    /** Cancel a Freja eID authentication session. */
    public CompletableFuture<Void> cancelAuth(String id) {
        return transport.delete("/v3/freja/auth/" + id);
    }

    /** Cancel a Freja eID signing session. */
    public CompletableFuture<Void> cancelSign(String id) {
        return transport.delete("/v3/freja/sign/" + id);
    }

    /** Cancel a Freja eID age verification session. */
    public CompletableFuture<Void> cancelAgeVerification(String id) {
        return transport.delete("/v3/freja/age-verification/" + id);
    }

    /** Poll until the authentication session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<FrejaStatus> waitForAuth(String id, PollOptions opts) {
        return transport.poll(() -> authStatus(id), FrejaStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<FrejaStatus> waitForAuth(String id) {
        return waitForAuth(id, new PollOptions());
    }

    /** Poll until the signing session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<FrejaStatus> waitForSign(String id, PollOptions opts) {
        return transport.poll(() -> signStatus(id), FrejaStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<FrejaStatus> waitForSign(String id) {
        return waitForSign(id, new PollOptions());
    }

    /** Poll until the age verification session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<AgeVerificationStatus> waitForAgeVerification(String id, PollOptions opts) {
        return transport.poll(() -> ageVerificationStatus(id), AgeVerificationStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<AgeVerificationStatus> waitForAgeVerification(String id) {
        return waitForAgeVerification(id, new PollOptions());
    }
}

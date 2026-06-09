package se.idkollen.client.endpoints;

import se.idkollen.client.PollOptions;
import se.idkollen.client.internal.Transport;

import se.idkollen.client.models.*;

import java.util.concurrent.CompletableFuture;

/** Endpoint for MitID operations. */
public class MitIdEndpoint {
    private final Transport transport;

    public MitIdEndpoint(Transport transport) {
        this.transport = transport;
    }

    /** Start a MitID authentication session. */
    public CompletableFuture<MitIdStatus> auth(MitIdAuthRequest req) {
        return transport.post("/v3/mitid/auth", req, MitIdStatus.class);
    }

    /** Start a MitID backchannel authentication session. */
    public CompletableFuture<MitIdStatus> backchannelAuth(MitIdBackchannelAuthRequest req) {
        return transport.post("/v3/mitid/backchannel/auth", req, MitIdStatus.class);
    }

    /** Start a MitID signing session. */
    public CompletableFuture<MitIdStatus> sign(MitIdSignRequest req) {
        return transport.post("/v3/mitid/sign", req, MitIdStatus.class);
    }

    /** Start a MitID age verification session. */
    public CompletableFuture<AgeVerificationStatus> ageVerification(AgeVerificationRequest req) {
        return transport.post("/v3/mitid/age-verification", req, AgeVerificationStatus.class);
    }

    /** Poll the current status of a MitID authentication session. */
    public CompletableFuture<MitIdStatus> authStatus(String id) {
        return transport.get("/v3/mitid/auth/" + id, MitIdStatus.class);
    }

    /** Poll the current status of a MitID signing session. */
    public CompletableFuture<MitIdStatus> signStatus(String id) {
        return transport.get("/v3/mitid/sign/" + id, MitIdStatus.class);
    }

    /** Poll the current status of a MitID age verification session. */
    public CompletableFuture<AgeVerificationStatus> ageVerificationStatus(String id) {
        return transport.get("/v3/mitid/age-verification/" + id, AgeVerificationStatus.class);
    }

    /** Cancel a MitID authentication session. */
    public CompletableFuture<Void> cancelAuth(String id) {
        return transport.delete("/v3/mitid/auth/" + id);
    }

    /** Cancel a MitID signing session. */
    public CompletableFuture<Void> cancelSign(String id) {
        return transport.delete("/v3/mitid/sign/" + id);
    }

    /** Cancel a MitID age verification session. */
    public CompletableFuture<Void> cancelAgeVerification(String id) {
        return transport.delete("/v3/mitid/age-verification/" + id);
    }

    /** Poll until the authentication session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<MitIdStatus> waitForAuth(String id, PollOptions opts) {
        return transport.poll(() -> authStatus(id), MitIdStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<MitIdStatus> waitForAuth(String id) {
        return waitForAuth(id, new PollOptions());
    }

    /** Poll until the signing session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<MitIdStatus> waitForSign(String id, PollOptions opts) {
        return transport.poll(() -> signStatus(id), MitIdStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<MitIdStatus> waitForSign(String id) {
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

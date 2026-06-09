package se.idkollen.client.endpoints;

import se.idkollen.client.PollOptions;
import se.idkollen.client.internal.Transport;

import se.idkollen.client.models.*;

import java.util.concurrent.CompletableFuture;

/** Endpoint for Finnish Trust Network (FTN) operations. */
public class FtnEndpoint {
    private final Transport transport;

    public FtnEndpoint(Transport transport) {
        this.transport = transport;
    }

    /** Start an FTN authentication session. */
    public CompletableFuture<FtnStatus> auth(FtnAuthRequest req) {
        return transport.post("/v3/ftn/auth", req, FtnStatus.class);
    }

    /** Start an FTN age verification session. */
    public CompletableFuture<AgeVerificationStatus> ageVerification(AgeVerificationRequest req) {
        return transport.post("/v3/ftn/age-verification", req, AgeVerificationStatus.class);
    }

    /** Poll the current status of an FTN authentication session. */
    public CompletableFuture<FtnStatus> authStatus(String id) {
        return transport.get("/v3/ftn/auth/" + id, FtnStatus.class);
    }

    /** Poll the current status of an FTN age verification session. */
    public CompletableFuture<AgeVerificationStatus> ageVerificationStatus(String id) {
        return transport.get("/v3/ftn/age-verification/" + id, AgeVerificationStatus.class);
    }

    /** Cancel an FTN authentication session. */
    public CompletableFuture<Void> cancelAuth(String id) {
        return transport.delete("/v3/ftn/auth/" + id);
    }

    /** Cancel an FTN age verification session. */
    public CompletableFuture<Void> cancelAgeVerification(String id) {
        return transport.delete("/v3/ftn/age-verification/" + id);
    }

    /** Poll until the authentication session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<FtnStatus> waitForAuth(String id, PollOptions opts) {
        return transport.poll(() -> authStatus(id), FtnStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<FtnStatus> waitForAuth(String id) {
        return waitForAuth(id, new PollOptions());
    }

    /** Poll until the age verification session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<AgeVerificationStatus> waitForAgeVerification(String id, PollOptions opts) {
        return transport.poll(() -> ageVerificationStatus(id), AgeVerificationStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<AgeVerificationStatus> waitForAgeVerification(String id) {
        return waitForAgeVerification(id, new PollOptions());
    }
}

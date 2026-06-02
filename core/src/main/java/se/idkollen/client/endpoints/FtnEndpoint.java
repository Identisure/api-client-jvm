package se.idkollen.client.endpoints;

import se.idkollen.client.IdkollenClient;
import se.idkollen.client.PollOptions;
import se.idkollen.client.models.*;

import java.util.concurrent.CompletableFuture;

/** Endpoint for Finnish Trust Network (FTN) operations. */
public class FtnEndpoint {
    private final IdkollenClient client;

    public FtnEndpoint(IdkollenClient client) {
        this.client = client;
    }

    /** Start an FTN authentication session. */
    public CompletableFuture<FtnStatus> authAsync(FtnAuthRequest req) {
        return client.post("/v3/ftn/auth", req, FtnStatus.class);
    }

    /** Start an FTN age verification session. */
    public CompletableFuture<AgeVerificationStatus> ageVerificationAsync(AgeVerificationRequest req) {
        return client.post("/v3/ftn/age-verification", req, AgeVerificationStatus.class);
    }

    /** Poll the current status of an FTN authentication session. */
    public CompletableFuture<FtnStatus> authStatusAsync(String id) {
        return client.get("/v3/ftn/auth/" + id, FtnStatus.class);
    }

    /** Poll the current status of an FTN age verification session. */
    public CompletableFuture<AgeVerificationStatus> ageVerificationStatusAsync(String id) {
        return client.get("/v3/ftn/age-verification/" + id, AgeVerificationStatus.class);
    }

    /** Cancel an FTN authentication session. */
    public CompletableFuture<Void> cancelAuthAsync(String id) {
        return client.delete("/v3/ftn/auth/" + id);
    }

    /** Cancel an FTN age verification session. */
    public CompletableFuture<Void> cancelAgeVerificationAsync(String id) {
        return client.delete("/v3/ftn/age-verification/" + id);
    }

    /** Poll until the authentication session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<FtnStatus> waitForAuthAsync(String id, PollOptions opts) {
        return client.poll(() -> authStatusAsync(id), FtnStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<FtnStatus> waitForAuthAsync(String id) {
        return waitForAuthAsync(id, new PollOptions());
    }

    /** Poll until the age verification session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<AgeVerificationStatus> waitForAgeVerificationAsync(String id, PollOptions opts) {
        return client.poll(() -> ageVerificationStatusAsync(id), AgeVerificationStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<AgeVerificationStatus> waitForAgeVerificationAsync(String id) {
        return waitForAgeVerificationAsync(id, new PollOptions());
    }
}

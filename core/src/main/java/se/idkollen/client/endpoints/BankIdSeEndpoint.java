package se.idkollen.client.endpoints;

import se.idkollen.client.IdkollenClient;
import se.idkollen.client.PollOptions;
import se.idkollen.client.models.*;

import java.util.concurrent.CompletableFuture;

/** Endpoint for BankID SE operations. */
public class BankIdSeEndpoint {
    private final IdkollenClient client;

    public BankIdSeEndpoint(IdkollenClient client) {
        this.client = client;
    }

    /** Start a BankID SE authentication session. */
    public CompletableFuture<BankIdSeStatus> authAsync(BankIdSeAuthRequest req) {
        return client.post("/v3/bankid-se/auth", req, BankIdSeStatus.class);
    }

    /** Start a BankID SE phone authentication session. */
    public CompletableFuture<BankIdSeStatus> phoneAuthAsync(BankIdSePhoneAuthRequest req) {
        return client.post("/v3/bankid-se/phone/auth", req, BankIdSeStatus.class);
    }

    /** Start a BankID SE signing session. */
    public CompletableFuture<BankIdSeStatus> signAsync(BankIdSeSignRequest req) {
        return client.post("/v3/bankid-se/sign", req, BankIdSeStatus.class);
    }

    /** Start a BankID SE phone signing session. */
    public CompletableFuture<BankIdSeStatus> phoneSignAsync(BankIdSePhoneSignRequest req) {
        return client.post("/v3/bankid-se/phone/sign", req, BankIdSeStatus.class);
    }

    /** Verify a scanned BankID SE QR code. */
    public CompletableFuture<BankIdSeVerifyResponse> verifyAsync(BankIdSeVerifyRequest req) {
        return client.post("/v3/bankid-se/verify", req, BankIdSeVerifyResponse.class);
    }

    /** Start a BankID SE age verification session. */
    public CompletableFuture<AgeVerificationStatus> ageVerificationAsync(AgeVerificationRequest req) {
        return client.post("/v3/bankid-se/age-verification", req, AgeVerificationStatus.class);
    }

    /** Poll the current status of a BankID SE authentication session. */
    public CompletableFuture<BankIdSeStatus> authStatusAsync(String id) {
        return client.get("/v3/bankid-se/auth/" + id, BankIdSeStatus.class);
    }

    /** Poll the current status of a BankID SE signing session. */
    public CompletableFuture<BankIdSeStatus> signStatusAsync(String id) {
        return client.get("/v3/bankid-se/sign/" + id, BankIdSeStatus.class);
    }

    /** Poll the current status of a BankID SE age verification session. */
    public CompletableFuture<AgeVerificationStatus> ageVerificationStatusAsync(String id) {
        return client.get("/v3/bankid-se/age-verification/" + id, AgeVerificationStatus.class);
    }

    /** Cancel a BankID SE authentication session. */
    public CompletableFuture<Void> cancelAuthAsync(String id) {
        return client.delete("/v3/bankid-se/auth/" + id);
    }

    /** Cancel a BankID SE signing session. */
    public CompletableFuture<Void> cancelSignAsync(String id) {
        return client.delete("/v3/bankid-se/sign/" + id);
    }

    /** Cancel a BankID SE age verification session. */
    public CompletableFuture<Void> cancelAgeVerificationAsync(String id) {
        return client.delete("/v3/bankid-se/age-verification/" + id);
    }

    /** Poll until the authentication session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<BankIdSeStatus> waitForAuthAsync(String id, PollOptions opts) {
        return client.poll(() -> authStatusAsync(id), BankIdSeStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<BankIdSeStatus> waitForAuthAsync(String id) {
        return waitForAuthAsync(id, new PollOptions());
    }

    /** Poll until the signing session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<BankIdSeStatus> waitForSignAsync(String id, PollOptions opts) {
        return client.poll(() -> signStatusAsync(id), BankIdSeStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<BankIdSeStatus> waitForSignAsync(String id) {
        return waitForSignAsync(id, new PollOptions());
    }

    /** Poll until the age verification session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<AgeVerificationStatus> waitForAgeVerificationAsync(String id, PollOptions opts) {
        return client.poll(() -> ageVerificationStatusAsync(id), AgeVerificationStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<AgeVerificationStatus> waitForAgeVerificationAsync(String id) {
        return waitForAgeVerificationAsync(id, new PollOptions());
    }
}

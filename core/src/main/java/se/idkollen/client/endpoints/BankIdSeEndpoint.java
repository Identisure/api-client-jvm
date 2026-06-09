package se.idkollen.client.endpoints;

import se.idkollen.client.PollOptions;
import se.idkollen.client.internal.Transport;

import se.idkollen.client.models.*;

import java.util.concurrent.CompletableFuture;

/** Endpoint for BankID SE operations. */
public class BankIdSeEndpoint {
    private final Transport transport;

    public BankIdSeEndpoint(Transport transport) {
        this.transport = transport;
    }

    /** Start a BankID SE authentication session. */
    public CompletableFuture<BankIdSeStatus> auth(BankIdSeAuthRequest req) {
        return transport.post("/v3/bankid-se/auth", req, BankIdSeStatus.class);
    }

    /** Start a BankID SE phone authentication session. */
    public CompletableFuture<BankIdSePhoneStatus> phoneAuth(BankIdSePhoneAuthRequest req) {
        return transport.post("/v3/bankid-se/phone/auth", req, BankIdSePhoneStatus.class);
    }

    /** Start a BankID SE signing session. */
    public CompletableFuture<BankIdSeStatus> sign(BankIdSeSignRequest req) {
        return transport.post("/v3/bankid-se/sign", req, BankIdSeStatus.class);
    }

    /** Start a BankID SE phone signing session. */
    public CompletableFuture<BankIdSePhoneStatus> phoneSign(BankIdSePhoneSignRequest req) {
        return transport.post("/v3/bankid-se/phone/sign", req, BankIdSePhoneStatus.class);
    }

    /** Verify a scanned BankID SE QR code. */
    public CompletableFuture<BankIdSeVerifyResponse> verify(BankIdSeVerifyRequest req) {
        return transport.post("/v3/bankid-se/verify", req, BankIdSeVerifyResponse.class);
    }

    /** Start a BankID SE age verification session. */
    public CompletableFuture<AgeVerificationStatus> ageVerification(AgeVerificationRequest req) {
        return transport.post("/v3/bankid-se/age-verification", req, AgeVerificationStatus.class);
    }

    /** Poll the current status of a BankID SE authentication session. */
    public CompletableFuture<BankIdSeStatus> authStatus(String id) {
        return transport.get("/v3/bankid-se/auth/" + id, BankIdSeStatus.class);
    }

    /** Poll the current status of a BankID SE signing session. */
    public CompletableFuture<BankIdSeStatus> signStatus(String id) {
        return transport.get("/v3/bankid-se/sign/" + id, BankIdSeStatus.class);
    }

    /** Poll the current status of a BankID SE age verification session. */
    public CompletableFuture<AgeVerificationStatus> ageVerificationStatus(String id) {
        return transport.get("/v3/bankid-se/age-verification/" + id, AgeVerificationStatus.class);
    }

    /** Cancel a BankID SE authentication session. */
    public CompletableFuture<Void> cancelAuth(String id) {
        return transport.delete("/v3/bankid-se/auth/" + id);
    }

    /** Cancel a BankID SE signing session. */
    public CompletableFuture<Void> cancelSign(String id) {
        return transport.delete("/v3/bankid-se/sign/" + id);
    }

    /** Cancel a BankID SE age verification session. */
    public CompletableFuture<Void> cancelAgeVerification(String id) {
        return transport.delete("/v3/bankid-se/age-verification/" + id);
    }

    /** Poll until the authentication session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<BankIdSeStatus> waitForAuth(String id, PollOptions opts) {
        return transport.poll(() -> authStatus(id), BankIdSeStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<BankIdSeStatus> waitForAuth(String id) {
        return waitForAuth(id, new PollOptions());
    }

    /** Poll until the signing session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<BankIdSeStatus> waitForSign(String id, PollOptions opts) {
        return transport.poll(() -> signStatus(id), BankIdSeStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<BankIdSeStatus> waitForSign(String id) {
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

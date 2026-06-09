package se.idkollen.client.endpoints;

import se.idkollen.client.PollOptions;
import se.idkollen.client.internal.Transport;

import se.idkollen.client.models.*;

import java.util.concurrent.CompletableFuture;

/** Endpoint for BankID NO operations. */
public class BankIdNoEndpoint {
    private final Transport transport;

    public BankIdNoEndpoint(Transport transport) {
        this.transport = transport;
    }

    /** Start a BankID NO authentication session. */
    public CompletableFuture<BankIdNoStatus> auth(BankIdNoAuthRequest req) {
        return transport.post("/v3/bankid-no/auth", req, BankIdNoStatus.class);
    }

    /** Start a BankID NO backchannel authentication session. */
    public CompletableFuture<BankIdNoStatus> backchannelAuth(BankIdNoBackchannelAuthRequest req) {
        return transport.post("/v3/bankid-no/backchannel/auth", req, BankIdNoStatus.class);
    }

    /** Start a BankID NO signing session. */
    public CompletableFuture<BankIdNoStatus> sign(BankIdNoSignRequest req) {
        return transport.post("/v3/bankid-no/sign", req, BankIdNoStatus.class);
    }

    /** Start a BankID NO age verification session. */
    public CompletableFuture<AgeVerificationStatus> ageVerification(AgeVerificationRequest req) {
        return transport.post("/v3/bankid-no/age-verification", req, AgeVerificationStatus.class);
    }

    /** Poll the current status of a BankID NO authentication session. */
    public CompletableFuture<BankIdNoStatus> authStatus(String id) {
        return transport.get("/v3/bankid-no/auth/" + id, BankIdNoStatus.class);
    }

    /** Poll the current status of a BankID NO signing session. */
    public CompletableFuture<BankIdNoStatus> signStatus(String id) {
        return transport.get("/v3/bankid-no/sign/" + id, BankIdNoStatus.class);
    }

    /** Poll the current status of a BankID NO age verification session. */
    public CompletableFuture<AgeVerificationStatus> ageVerificationStatus(String id) {
        return transport.get("/v3/bankid-no/age-verification/" + id, AgeVerificationStatus.class);
    }

    /** Cancel a BankID NO authentication session. */
    public CompletableFuture<Void> cancelAuth(String id) {
        return transport.delete("/v3/bankid-no/auth/" + id);
    }

    /** Cancel a BankID NO signing session. */
    public CompletableFuture<Void> cancelSign(String id) {
        return transport.delete("/v3/bankid-no/sign/" + id);
    }

    /** Cancel a BankID NO age verification session. */
    public CompletableFuture<Void> cancelAgeVerification(String id) {
        return transport.delete("/v3/bankid-no/age-verification/" + id);
    }

    /** Poll until the authentication session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<BankIdNoStatus> waitForAuth(String id, PollOptions opts) {
        return transport.poll(() -> authStatus(id), BankIdNoStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<BankIdNoStatus> waitForAuth(String id) {
        return waitForAuth(id, new PollOptions());
    }

    /** Poll until the signing session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<BankIdNoStatus> waitForSign(String id, PollOptions opts) {
        return transport.poll(() -> signStatus(id), BankIdNoStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<BankIdNoStatus> waitForSign(String id) {
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

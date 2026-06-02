package se.idkollen.client.endpoints;

import se.idkollen.client.IdkollenClient;
import se.idkollen.client.PollOptions;
import se.idkollen.client.models.*;

import java.util.concurrent.CompletableFuture;

/** Endpoint for BankID NO operations. */
public class BankIdNoEndpoint {
    private final IdkollenClient client;

    public BankIdNoEndpoint(IdkollenClient client) {
        this.client = client;
    }

    /** Start a BankID NO authentication session. */
    public CompletableFuture<BankIdNoStatus> authAsync(BankIdNoAuthRequest req) {
        return client.post("/v3/bankid-no/auth", req, BankIdNoStatus.class);
    }

    /** Start a BankID NO backchannel authentication session. */
    public CompletableFuture<BankIdNoStatus> backchannelAuthAsync(BankIdNoBackchannelAuthRequest req) {
        return client.post("/v3/bankid-no/backchannel/auth", req, BankIdNoStatus.class);
    }

    /** Start a BankID NO signing session. */
    public CompletableFuture<BankIdNoStatus> signAsync(BankIdNoSignRequest req) {
        return client.post("/v3/bankid-no/sign", req, BankIdNoStatus.class);
    }

    /** Poll the current status of a BankID NO authentication session. */
    public CompletableFuture<BankIdNoStatus> authStatusAsync(String id) {
        return client.get("/v3/bankid-no/auth/" + id, BankIdNoStatus.class);
    }

    /** Poll the current status of a BankID NO signing session. */
    public CompletableFuture<BankIdNoStatus> signStatusAsync(String id) {
        return client.get("/v3/bankid-no/sign/" + id, BankIdNoStatus.class);
    }

    /** Cancel a BankID NO authentication session. */
    public CompletableFuture<Void> cancelAuthAsync(String id) {
        return client.delete("/v3/bankid-no/auth/" + id);
    }

    /** Cancel a BankID NO signing session. */
    public CompletableFuture<Void> cancelSignAsync(String id) {
        return client.delete("/v3/bankid-no/sign/" + id);
    }

    /** Poll until the authentication session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<BankIdNoStatus> waitForAuthAsync(String id, PollOptions opts) {
        return client.poll(() -> authStatusAsync(id), BankIdNoStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<BankIdNoStatus> waitForAuthAsync(String id) {
        return waitForAuthAsync(id, new PollOptions());
    }

    /** Poll until the signing session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<BankIdNoStatus> waitForSignAsync(String id, PollOptions opts) {
        return client.poll(() -> signStatusAsync(id), BankIdNoStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<BankIdNoStatus> waitForSignAsync(String id) {
        return waitForSignAsync(id, new PollOptions());
    }
}

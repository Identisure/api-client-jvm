package se.idkollen.client.endpoints;

import se.idkollen.client.IdkollenClient;
import se.idkollen.client.PollOptions;
import se.idkollen.client.models.*;

import java.util.concurrent.CompletableFuture;

/** Endpoint for MitID operations. */
public class MitIdEndpoint {
    private final IdkollenClient client;

    public MitIdEndpoint(IdkollenClient client) {
        this.client = client;
    }

    /** Start a MitID authentication session. */
    public CompletableFuture<MitIdStatus> authAsync(MitIdAuthRequest req) {
        return client.post("/v3/mitid/auth", req, MitIdStatus.class);
    }

    /** Start a MitID backchannel authentication session. */
    public CompletableFuture<MitIdStatus> backchannelAuthAsync(MitIdBackchannelAuthRequest req) {
        return client.post("/v3/mitid/backchannel/auth", req, MitIdStatus.class);
    }

    /** Start a MitID signing session. */
    public CompletableFuture<MitIdStatus> signAsync(MitIdSignRequest req) {
        return client.post("/v3/mitid/sign", req, MitIdStatus.class);
    }

    /** Poll the current status of a MitID authentication session. */
    public CompletableFuture<MitIdStatus> authStatusAsync(String id) {
        return client.get("/v3/mitid/auth/" + id, MitIdStatus.class);
    }

    /** Poll the current status of a MitID signing session. */
    public CompletableFuture<MitIdStatus> signStatusAsync(String id) {
        return client.get("/v3/mitid/sign/" + id, MitIdStatus.class);
    }

    /** Cancel a MitID authentication session. */
    public CompletableFuture<Void> cancelAuthAsync(String id) {
        return client.delete("/v3/mitid/auth/" + id);
    }

    /** Cancel a MitID signing session. */
    public CompletableFuture<Void> cancelSignAsync(String id) {
        return client.delete("/v3/mitid/sign/" + id);
    }

    /** Poll until the authentication session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<MitIdStatus> waitForAuthAsync(String id, PollOptions opts) {
        return client.poll(() -> authStatusAsync(id), MitIdStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<MitIdStatus> waitForAuthAsync(String id) {
        return waitForAuthAsync(id, new PollOptions());
    }

    /** Poll until the signing session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<MitIdStatus> waitForSignAsync(String id, PollOptions opts) {
        return client.poll(() -> signStatusAsync(id), MitIdStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<MitIdStatus> waitForSignAsync(String id) {
        return waitForSignAsync(id, new PollOptions());
    }
}

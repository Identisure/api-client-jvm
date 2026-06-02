package se.idkollen.client.endpoints;

import se.idkollen.client.IdkollenClient;
import se.idkollen.client.PollOptions;
import se.idkollen.client.models.*;

import java.util.concurrent.CompletableFuture;

/** Endpoint for Freja eID operations. */
public class FrejaEndpoint {
    private final IdkollenClient client;

    public FrejaEndpoint(IdkollenClient client) {
        this.client = client;
    }

    /** Start a Freja eID authentication session. */
    public CompletableFuture<FrejaStatus> authAsync(FrejaAuthRequest req) {
        return client.post("/v3/freja/auth", req, FrejaStatus.class);
    }

    /** Start a Freja eID backchannel authentication session. */
    public CompletableFuture<FrejaStatus> backchannelAuthAsync(FrejaBackchannelAuthRequest req) {
        return client.post("/v3/freja/backchannel/auth", req, FrejaStatus.class);
    }

    /** Start a Freja eID signing session. */
    public CompletableFuture<FrejaStatus> signAsync(FrejaSignRequest req) {
        return client.post("/v3/freja/sign", req, FrejaStatus.class);
    }

    /** Start a Freja eID backchannel signing session. */
    public CompletableFuture<FrejaStatus> backchannelSignAsync(FrejaBackchannelSignRequest req) {
        return client.post("/v3/freja/backchannel/sign", req, FrejaStatus.class);
    }

    /** Poll the current status of a Freja eID authentication session. */
    public CompletableFuture<FrejaStatus> authStatusAsync(String id) {
        return client.get("/v3/freja/auth/" + id, FrejaStatus.class);
    }

    /** Poll the current status of a Freja eID signing session. */
    public CompletableFuture<FrejaStatus> signStatusAsync(String id) {
        return client.get("/v3/freja/sign/" + id, FrejaStatus.class);
    }

    /** Cancel a Freja eID authentication session. */
    public CompletableFuture<Void> cancelAuthAsync(String id) {
        return client.delete("/v3/freja/auth/" + id);
    }

    /** Cancel a Freja eID signing session. */
    public CompletableFuture<Void> cancelSignAsync(String id) {
        return client.delete("/v3/freja/sign/" + id);
    }

    /** Poll until the authentication session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<FrejaStatus> waitForAuthAsync(String id, PollOptions opts) {
        return client.poll(() -> authStatusAsync(id), FrejaStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<FrejaStatus> waitForAuthAsync(String id) {
        return waitForAuthAsync(id, new PollOptions());
    }

    /** Poll until the signing session reaches a terminal state or the timeout elapses. */
    public CompletableFuture<FrejaStatus> waitForSignAsync(String id, PollOptions opts) {
        return client.poll(() -> signStatusAsync(id), FrejaStatus.Pending.class::isInstance, opts);
    }

    public CompletableFuture<FrejaStatus> waitForSignAsync(String id) {
        return waitForSignAsync(id, new PollOptions());
    }
}

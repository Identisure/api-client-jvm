package se.idkollen.client;

/** Options controlling the behaviour of the high-level {@code waitFor*} polling helpers. */
public record PollOptions(long intervalMs, long timeoutMs) {
    /** Create options with default values: 2 s interval, 300 s timeout. */
    public PollOptions() {
        this(2_000L, 300_000L);
    }
}

package se.idkollen.client.models;

/** Whether the user or the relying party (RP) initiated the phone call. */
public enum CallInitiator {
    /** The user called the RP. */
    USER,
    /** The RP called the user. */
    RP,
}

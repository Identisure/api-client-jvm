package se.idkollen.client.models;

/** Response from the document upload endpoint. */
public class DocumentUploadResponse {
    /** Document UUID — pass this as a reference in signing requests. */
    public String id;
    /** SHA hash of the uploaded document for integrity verification. */
    public String hash;
}

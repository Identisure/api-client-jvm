package se.idkollen.client.endpoints;

import se.idkollen.client.PollOptions;
import se.idkollen.client.internal.Transport;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import se.idkollen.client.models.DocumentUploadResponse;

import java.util.concurrent.CompletableFuture;

/** Endpoint for document upload and download operations. */
public class DocumentEndpoint {
    private final Transport transport;

    public DocumentEndpoint(Transport transport) {
        this.transport = transport;
    }

    /**
     * Upload a document for use in signing sessions.
     *
     * @param bytes    Raw file bytes.
     * @param filename File name sent in the multipart form.
     * @param mimeType MIME type of the file.
     * @return Upload response containing the document UUID and hash.
     */
    public CompletableFuture<DocumentUploadResponse> upload(byte[] bytes, String filename, String mimeType) {
        var fileBody = RequestBody.create(bytes, MediaType.get(mimeType));
        var form = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", filename, fileBody)
            .build();

        return transport.postMultipart("/v3/document", form, DocumentUploadResponse.class);
    }

    /** Upload a document with the default MIME type {@code application/pdf}. */
    public CompletableFuture<DocumentUploadResponse> upload(byte[] bytes, String filename) {
        return upload(bytes, filename, "application/pdf");
    }

    /**
     * Download a previously uploaded or signed document.
     *
     * @return Raw file bytes.
     */
    public CompletableFuture<byte[]> download(String id) {
        return transport.getBytes("/v3/document/" + id);
    }

    /** Delete a previously uploaded document. */
    public CompletableFuture<Void> delete(String id) {
        return transport.delete("/v3/document/" + id);
    }
}

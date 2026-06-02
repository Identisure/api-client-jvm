package se.idkollen.client.endpoints;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import se.idkollen.client.IdkollenClient;
import se.idkollen.client.models.DocumentUploadResponse;

import java.util.concurrent.CompletableFuture;

/** Endpoint for document upload and download operations. */
public class DocumentEndpoint {
    private final IdkollenClient client;

    public DocumentEndpoint(IdkollenClient client) {
        this.client = client;
    }

    /**
     * Upload a document for use in signing sessions.
     *
     * @param bytes    Raw file bytes.
     * @param filename File name sent in the multipart form.
     * @param mimeType MIME type of the file.
     * @return Upload response containing the document UUID and hash.
     */
    public CompletableFuture<DocumentUploadResponse> uploadAsync(byte[] bytes, String filename, String mimeType) {
        var fileBody = RequestBody.create(bytes, MediaType.get(mimeType));
        var form = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", filename, fileBody)
            .build();

        return client.postMultipart("/v3/document", form, DocumentUploadResponse.class);
    }

    /** Upload a document with the default MIME type {@code application/pdf}. */
    public CompletableFuture<DocumentUploadResponse> uploadAsync(byte[] bytes, String filename) {
        return uploadAsync(bytes, filename, "application/pdf");
    }

    /**
     * Download a previously uploaded or signed document.
     *
     * @return Raw file bytes.
     */
    public CompletableFuture<byte[]> downloadAsync(String id) {
        return client.getBytes("/v3/document/" + id);
    }

    /** Delete a previously uploaded document. */
    public CompletableFuture<Void> deleteAsync(String id) {
        return client.delete("/v3/document/" + id);
    }
}

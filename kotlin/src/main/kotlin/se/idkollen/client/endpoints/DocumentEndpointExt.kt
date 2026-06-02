package se.idkollen.client

import kotlinx.coroutines.future.await
import se.idkollen.client.endpoints.DocumentEndpoint
import se.idkollen.client.models.DocumentUploadResponse

suspend fun DocumentEndpoint.upload(bytes: ByteArray, filename: String, mimeType: String = "application/pdf"): DocumentUploadResponse =
    uploadAsync(bytes, filename, mimeType).await()

suspend fun DocumentEndpoint.download(id: String): ByteArray = downloadAsync(id).await()

suspend fun DocumentEndpoint.delete(id: String) = deleteAsync(id).await()

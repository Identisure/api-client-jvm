package se.idkollen.client

import kotlinx.coroutines.future.await
import se.idkollen.client.endpoints.DocumentEndpoint
import se.idkollen.client.models.DocumentUploadResponse

suspend fun DocumentEndpoint.uploadAsync(bytes: ByteArray, filename: String, mimeType: String = "application/pdf"): DocumentUploadResponse =
    upload(bytes, filename, mimeType).await()

suspend fun DocumentEndpoint.downloadAsync(id: String): ByteArray = download(id).await()

suspend fun DocumentEndpoint.deleteAsync(id: String) = delete(id).await()

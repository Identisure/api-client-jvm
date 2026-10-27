package com.identisure.client

import com.identisure.client.endpoints.DocumentEndpoint
import com.identisure.client.models.DocumentUploadResponse
import kotlinx.coroutines.future.await

suspend fun DocumentEndpoint.uploadAsync(
    bytes: ByteArray,
    filename: String,
    mimeType: String = "application/pdf",
): DocumentUploadResponse = upload(bytes, filename, mimeType).await()

suspend fun DocumentEndpoint.downloadAsync(id: String): ByteArray = download(id).await()

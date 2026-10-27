package com.identisure.client

import kotlinx.coroutines.future.await
import com.identisure.client.endpoints.DocumentEndpoint
import com.identisure.client.models.DocumentUploadResponse

suspend fun DocumentEndpoint.uploadAsync(bytes: ByteArray, filename: String, mimeType: String = "application/pdf"): DocumentUploadResponse =
    upload(bytes, filename, mimeType).await()

suspend fun DocumentEndpoint.downloadAsync(id: String): ByteArray = download(id).await()

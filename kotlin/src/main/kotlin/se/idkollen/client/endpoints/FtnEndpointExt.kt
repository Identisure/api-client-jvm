package se.idkollen.client

import kotlinx.coroutines.future.await
import se.idkollen.client.endpoints.FtnEndpoint
import se.idkollen.client.models.*

suspend fun FtnEndpoint.auth(req: FtnAuthRequest): FtnStatus = authAsync(req).await()
suspend fun FtnEndpoint.ageVerification(req: AgeVerificationRequest): AgeVerificationStatus = ageVerificationAsync(req).await()
suspend fun FtnEndpoint.authStatus(id: String): FtnStatus = authStatusAsync(id).await()
suspend fun FtnEndpoint.ageVerificationStatus(id: String): AgeVerificationStatus = ageVerificationStatusAsync(id).await()
suspend fun FtnEndpoint.cancelAuth(id: String) = cancelAuthAsync(id).await()
suspend fun FtnEndpoint.cancelAgeVerification(id: String) = cancelAgeVerificationAsync(id).await()
suspend fun FtnEndpoint.waitForAuth(id: String, opts: PollOptions = PollOptions()): FtnStatus = waitForAuthAsync(id, opts).await()
suspend fun FtnEndpoint.waitForAgeVerification(id: String, opts: PollOptions = PollOptions()): AgeVerificationStatus = waitForAgeVerificationAsync(id, opts).await()

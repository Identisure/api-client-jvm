package se.idkollen.client

import kotlinx.coroutines.future.await
import se.idkollen.client.endpoints.FtnEndpoint
import se.idkollen.client.models.*

suspend fun FtnEndpoint.authAsync(req: FtnAuthRequest): FtnStatus = auth(req).await()
suspend fun FtnEndpoint.ageVerificationAsync(req: AgeVerificationRequest): AgeVerificationStatus = ageVerification(req).await()
suspend fun FtnEndpoint.authStatusAsync(id: String): FtnStatus = authStatus(id).await()
suspend fun FtnEndpoint.ageVerificationStatusAsync(id: String): AgeVerificationStatus = ageVerificationStatus(id).await()
suspend fun FtnEndpoint.cancelAuthAsync(id: String) = cancelAuth(id).await()
suspend fun FtnEndpoint.cancelAgeVerificationAsync(id: String) = cancelAgeVerification(id).await()
suspend fun FtnEndpoint.waitForAuthAsync(id: String, opts: PollOptions = PollOptions()): FtnStatus = waitForAuth(id, opts).await()
suspend fun FtnEndpoint.waitForAgeVerificationAsync(id: String, opts: PollOptions = PollOptions()): AgeVerificationStatus = waitForAgeVerification(id, opts).await()

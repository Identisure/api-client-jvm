package se.idkollen.client

import kotlinx.coroutines.future.await
import se.idkollen.client.endpoints.MitIdEndpoint
import se.idkollen.client.models.*

suspend fun MitIdEndpoint.authAsync(req: MitIdAuthRequest): MitIdStatus = auth(req).await()
suspend fun MitIdEndpoint.ageVerificationAsync(req: AgeVerificationRequest): AgeVerificationStatus = ageVerification(req).await()
suspend fun MitIdEndpoint.backchannelAuthAsync(req: MitIdBackchannelAuthRequest): MitIdStatus = backchannelAuth(req).await()
suspend fun MitIdEndpoint.signAsync(req: MitIdSignRequest): MitIdStatus = sign(req).await()
suspend fun MitIdEndpoint.authStatusAsync(id: String): MitIdStatus = authStatus(id).await()
suspend fun MitIdEndpoint.signStatusAsync(id: String): MitIdStatus = signStatus(id).await()
suspend fun MitIdEndpoint.ageVerificationStatusAsync(id: String): AgeVerificationStatus = ageVerificationStatus(id).await()
suspend fun MitIdEndpoint.cancelAuthAsync(id: String) = cancelAuth(id).await()
suspend fun MitIdEndpoint.cancelSignAsync(id: String) = cancelSign(id).await()
suspend fun MitIdEndpoint.cancelAgeVerificationAsync(id: String) = cancelAgeVerification(id).await()
suspend fun MitIdEndpoint.waitForAuthAsync(id: String, opts: PollOptions = PollOptions()): MitIdStatus = waitForAuth(id, opts).await()
suspend fun MitIdEndpoint.waitForSignAsync(id: String, opts: PollOptions = PollOptions()): MitIdStatus = waitForSign(id, opts).await()
suspend fun MitIdEndpoint.waitForAgeVerificationAsync(id: String, opts: PollOptions = PollOptions()): AgeVerificationStatus = waitForAgeVerification(id, opts).await()

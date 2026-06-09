package se.idkollen.client

import kotlinx.coroutines.future.await
import se.idkollen.client.endpoints.FrejaEndpoint
import se.idkollen.client.models.*

suspend fun FrejaEndpoint.authAsync(req: FrejaAuthRequest): FrejaStatus = auth(req).await()
suspend fun FrejaEndpoint.ageVerificationAsync(req: AgeVerificationRequest): AgeVerificationStatus = ageVerification(req).await()
suspend fun FrejaEndpoint.backchannelAuthAsync(req: FrejaBackchannelAuthRequest): FrejaStatus = backchannelAuth(req).await()
suspend fun FrejaEndpoint.signAsync(req: FrejaSignRequest): FrejaStatus = sign(req).await()
suspend fun FrejaEndpoint.backchannelSignAsync(req: FrejaBackchannelSignRequest): FrejaStatus = backchannelSign(req).await()
suspend fun FrejaEndpoint.authStatusAsync(id: String): FrejaStatus = authStatus(id).await()
suspend fun FrejaEndpoint.signStatusAsync(id: String): FrejaStatus = signStatus(id).await()
suspend fun FrejaEndpoint.ageVerificationStatusAsync(id: String): AgeVerificationStatus = ageVerificationStatus(id).await()
suspend fun FrejaEndpoint.cancelAuthAsync(id: String) = cancelAuth(id).await()
suspend fun FrejaEndpoint.cancelSignAsync(id: String) = cancelSign(id).await()
suspend fun FrejaEndpoint.cancelAgeVerificationAsync(id: String) = cancelAgeVerification(id).await()
suspend fun FrejaEndpoint.waitForAuthAsync(id: String, opts: PollOptions = PollOptions()): FrejaStatus = waitForAuth(id, opts).await()
suspend fun FrejaEndpoint.waitForSignAsync(id: String, opts: PollOptions = PollOptions()): FrejaStatus = waitForSign(id, opts).await()
suspend fun FrejaEndpoint.waitForAgeVerificationAsync(id: String, opts: PollOptions = PollOptions()): AgeVerificationStatus = waitForAgeVerification(id, opts).await()

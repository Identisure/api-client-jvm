package se.idkollen.client

import kotlinx.coroutines.future.await
import se.idkollen.client.endpoints.MitIdEndpoint
import se.idkollen.client.models.*

suspend fun MitIdEndpoint.auth(req: MitIdAuthRequest): MitIdStatus = authAsync(req).await()
suspend fun MitIdEndpoint.backchannelAuth(req: MitIdBackchannelAuthRequest): MitIdStatus = backchannelAuthAsync(req).await()
suspend fun MitIdEndpoint.sign(req: MitIdSignRequest): MitIdStatus = signAsync(req).await()
suspend fun MitIdEndpoint.authStatus(id: String): MitIdStatus = authStatusAsync(id).await()
suspend fun MitIdEndpoint.signStatus(id: String): MitIdStatus = signStatusAsync(id).await()
suspend fun MitIdEndpoint.cancelAuth(id: String) = cancelAuthAsync(id).await()
suspend fun MitIdEndpoint.cancelSign(id: String) = cancelSignAsync(id).await()
suspend fun MitIdEndpoint.waitForAuth(id: String, opts: PollOptions = PollOptions()): MitIdStatus = waitForAuthAsync(id, opts).await()
suspend fun MitIdEndpoint.waitForSign(id: String, opts: PollOptions = PollOptions()): MitIdStatus = waitForSignAsync(id, opts).await()

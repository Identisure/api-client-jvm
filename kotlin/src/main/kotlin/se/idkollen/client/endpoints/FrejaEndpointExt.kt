package se.idkollen.client

import kotlinx.coroutines.future.await
import se.idkollen.client.endpoints.FrejaEndpoint
import se.idkollen.client.models.*

suspend fun FrejaEndpoint.auth(req: FrejaAuthRequest): FrejaStatus = authAsync(req).await()
suspend fun FrejaEndpoint.backchannelAuth(req: FrejaBackchannelAuthRequest): FrejaStatus = backchannelAuthAsync(req).await()
suspend fun FrejaEndpoint.sign(req: FrejaSignRequest): FrejaStatus = signAsync(req).await()
suspend fun FrejaEndpoint.backchannelSign(req: FrejaBackchannelSignRequest): FrejaStatus = backchannelSignAsync(req).await()
suspend fun FrejaEndpoint.authStatus(id: String): FrejaStatus = authStatusAsync(id).await()
suspend fun FrejaEndpoint.signStatus(id: String): FrejaStatus = signStatusAsync(id).await()
suspend fun FrejaEndpoint.cancelAuth(id: String) = cancelAuthAsync(id).await()
suspend fun FrejaEndpoint.cancelSign(id: String) = cancelSignAsync(id).await()
suspend fun FrejaEndpoint.waitForAuth(id: String, opts: PollOptions = PollOptions()): FrejaStatus = waitForAuthAsync(id, opts).await()
suspend fun FrejaEndpoint.waitForSign(id: String, opts: PollOptions = PollOptions()): FrejaStatus = waitForSignAsync(id, opts).await()

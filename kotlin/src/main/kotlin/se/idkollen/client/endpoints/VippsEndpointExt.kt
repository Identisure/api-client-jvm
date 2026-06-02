package se.idkollen.client

import kotlinx.coroutines.future.await
import se.idkollen.client.endpoints.VippsEndpoint
import se.idkollen.client.models.*

suspend fun VippsEndpoint.auth(req: VippsAuthRequest): VippsStatus = authAsync(req).await()
suspend fun VippsEndpoint.backchannelAuth(req: VippsBackchannelAuthRequest): VippsStatus = backchannelAuthAsync(req).await()
suspend fun VippsEndpoint.authStatus(id: String): VippsStatus = authStatusAsync(id).await()
suspend fun VippsEndpoint.cancelAuth(id: String) = cancelAuthAsync(id).await()
suspend fun VippsEndpoint.waitForAuth(id: String, opts: PollOptions = PollOptions()): VippsStatus = waitForAuthAsync(id, opts).await()

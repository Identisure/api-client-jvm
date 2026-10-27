package com.identisure.client

import com.identisure.client.models.*
import kotlinx.coroutines.future.await

suspend fun VippsEndpoint.authAsync(req: VippsAuthRequest): VippsStatus = auth(req).await()

suspend fun VippsEndpoint.backchannelAuthAsync(req: VippsBackchannelAuthRequest): VippsStatus =
    backchannelAuth(req).await()

suspend fun VippsEndpoint.authStatusAsync(id: String): VippsStatus = authStatus(id).await()

suspend fun VippsEndpoint.cancelAuthAsync(id: String) = cancelAuth(id).await()

suspend fun VippsEndpoint.waitForAuthAsync(
    id: String,
    opts: PollOptions = PollOptions(),
): VippsStatus = waitForAuth(id, opts).await()

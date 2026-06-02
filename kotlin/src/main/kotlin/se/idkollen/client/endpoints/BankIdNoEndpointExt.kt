package se.idkollen.client

import kotlinx.coroutines.future.await
import se.idkollen.client.endpoints.BankIdNoEndpoint
import se.idkollen.client.models.*

suspend fun BankIdNoEndpoint.auth(req: BankIdNoAuthRequest): BankIdNoStatus = authAsync(req).await()
suspend fun BankIdNoEndpoint.backchannelAuth(req: BankIdNoBackchannelAuthRequest): BankIdNoStatus = backchannelAuthAsync(req).await()
suspend fun BankIdNoEndpoint.sign(req: BankIdNoSignRequest): BankIdNoStatus = signAsync(req).await()
suspend fun BankIdNoEndpoint.authStatus(id: String): BankIdNoStatus = authStatusAsync(id).await()
suspend fun BankIdNoEndpoint.signStatus(id: String): BankIdNoStatus = signStatusAsync(id).await()
suspend fun BankIdNoEndpoint.cancelAuth(id: String) = cancelAuthAsync(id).await()
suspend fun BankIdNoEndpoint.cancelSign(id: String) = cancelSignAsync(id).await()
suspend fun BankIdNoEndpoint.waitForAuth(id: String, opts: PollOptions = PollOptions()): BankIdNoStatus = waitForAuthAsync(id, opts).await()
suspend fun BankIdNoEndpoint.waitForSign(id: String, opts: PollOptions = PollOptions()): BankIdNoStatus = waitForSignAsync(id, opts).await()

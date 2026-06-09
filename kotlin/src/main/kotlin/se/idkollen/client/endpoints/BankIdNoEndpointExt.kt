package se.idkollen.client

import kotlinx.coroutines.future.await
import se.idkollen.client.endpoints.BankIdNoEndpoint
import se.idkollen.client.models.*

suspend fun BankIdNoEndpoint.authAsync(req: BankIdNoAuthRequest): BankIdNoStatus = auth(req).await()
suspend fun BankIdNoEndpoint.ageVerificationAsync(req: AgeVerificationRequest): AgeVerificationStatus = ageVerification(req).await()
suspend fun BankIdNoEndpoint.backchannelAuthAsync(req: BankIdNoBackchannelAuthRequest): BankIdNoStatus = backchannelAuth(req).await()
suspend fun BankIdNoEndpoint.signAsync(req: BankIdNoSignRequest): BankIdNoStatus = sign(req).await()
suspend fun BankIdNoEndpoint.authStatusAsync(id: String): BankIdNoStatus = authStatus(id).await()
suspend fun BankIdNoEndpoint.signStatusAsync(id: String): BankIdNoStatus = signStatus(id).await()
suspend fun BankIdNoEndpoint.ageVerificationStatusAsync(id: String): AgeVerificationStatus = ageVerificationStatus(id).await()
suspend fun BankIdNoEndpoint.cancelAuthAsync(id: String) = cancelAuth(id).await()
suspend fun BankIdNoEndpoint.cancelSignAsync(id: String) = cancelSign(id).await()
suspend fun BankIdNoEndpoint.cancelAgeVerificationAsync(id: String) = cancelAgeVerification(id).await()
suspend fun BankIdNoEndpoint.waitForAuthAsync(id: String, opts: PollOptions = PollOptions()): BankIdNoStatus = waitForAuth(id, opts).await()
suspend fun BankIdNoEndpoint.waitForSignAsync(id: String, opts: PollOptions = PollOptions()): BankIdNoStatus = waitForSign(id, opts).await()
suspend fun BankIdNoEndpoint.waitForAgeVerificationAsync(id: String, opts: PollOptions = PollOptions()): AgeVerificationStatus = waitForAgeVerification(id, opts).await()

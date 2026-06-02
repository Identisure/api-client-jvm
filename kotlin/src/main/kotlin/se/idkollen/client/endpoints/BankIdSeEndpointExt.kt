package se.idkollen.client

import kotlinx.coroutines.future.await
import se.idkollen.client.endpoints.BankIdSeEndpoint
import se.idkollen.client.models.*

suspend fun BankIdSeEndpoint.auth(req: BankIdSeAuthRequest): BankIdSeStatus = authAsync(req).await()
suspend fun BankIdSeEndpoint.phoneAuth(req: BankIdSePhoneAuthRequest): BankIdSeStatus = phoneAuthAsync(req).await()
suspend fun BankIdSeEndpoint.sign(req: BankIdSeSignRequest): BankIdSeStatus = signAsync(req).await()
suspend fun BankIdSeEndpoint.phoneSign(req: BankIdSePhoneSignRequest): BankIdSeStatus = phoneSignAsync(req).await()
suspend fun BankIdSeEndpoint.verify(req: BankIdSeVerifyRequest): BankIdSeVerifyResponse = verifyAsync(req).await()
suspend fun BankIdSeEndpoint.ageVerification(req: AgeVerificationRequest): AgeVerificationStatus = ageVerificationAsync(req).await()
suspend fun BankIdSeEndpoint.authStatus(id: String): BankIdSeStatus = authStatusAsync(id).await()
suspend fun BankIdSeEndpoint.signStatus(id: String): BankIdSeStatus = signStatusAsync(id).await()
suspend fun BankIdSeEndpoint.ageVerificationStatus(id: String): AgeVerificationStatus = ageVerificationStatusAsync(id).await()
suspend fun BankIdSeEndpoint.cancelAuth(id: String) = cancelAuthAsync(id).await()
suspend fun BankIdSeEndpoint.cancelSign(id: String) = cancelSignAsync(id).await()
suspend fun BankIdSeEndpoint.cancelAgeVerification(id: String) = cancelAgeVerificationAsync(id).await()
suspend fun BankIdSeEndpoint.waitForAuth(id: String, opts: PollOptions = PollOptions()): BankIdSeStatus = waitForAuthAsync(id, opts).await()
suspend fun BankIdSeEndpoint.waitForSign(id: String, opts: PollOptions = PollOptions()): BankIdSeStatus = waitForSignAsync(id, opts).await()
suspend fun BankIdSeEndpoint.waitForAgeVerification(id: String, opts: PollOptions = PollOptions()): AgeVerificationStatus = waitForAgeVerificationAsync(id, opts).await()

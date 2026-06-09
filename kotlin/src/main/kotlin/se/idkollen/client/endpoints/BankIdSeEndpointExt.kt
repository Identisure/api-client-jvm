package se.idkollen.client

import kotlinx.coroutines.future.await
import se.idkollen.client.endpoints.BankIdSeEndpoint
import se.idkollen.client.models.*

suspend fun BankIdSeEndpoint.authAsync(req: BankIdSeAuthRequest): BankIdSeStatus = auth(req).await()
suspend fun BankIdSeEndpoint.phoneAuthAsync(req: BankIdSePhoneAuthRequest): BankIdSePhoneStatus = phoneAuth(req).await()
suspend fun BankIdSeEndpoint.signAsync(req: BankIdSeSignRequest): BankIdSeStatus = sign(req).await()
suspend fun BankIdSeEndpoint.phoneSignAsync(req: BankIdSePhoneSignRequest): BankIdSePhoneStatus = phoneSign(req).await()
suspend fun BankIdSeEndpoint.verifyAsync(req: BankIdSeVerifyRequest): BankIdSeVerifyResponse = verify(req).await()
suspend fun BankIdSeEndpoint.ageVerificationAsync(req: AgeVerificationRequest): AgeVerificationStatus = ageVerification(req).await()
suspend fun BankIdSeEndpoint.authStatusAsync(id: String): BankIdSeStatus = authStatus(id).await()
suspend fun BankIdSeEndpoint.signStatusAsync(id: String): BankIdSeStatus = signStatus(id).await()
suspend fun BankIdSeEndpoint.ageVerificationStatusAsync(id: String): AgeVerificationStatus = ageVerificationStatus(id).await()
suspend fun BankIdSeEndpoint.cancelAuthAsync(id: String) = cancelAuth(id).await()
suspend fun BankIdSeEndpoint.cancelSignAsync(id: String) = cancelSign(id).await()
suspend fun BankIdSeEndpoint.cancelAgeVerificationAsync(id: String) = cancelAgeVerification(id).await()
suspend fun BankIdSeEndpoint.waitForAuthAsync(id: String, opts: PollOptions = PollOptions()): BankIdSeStatus = waitForAuth(id, opts).await()
suspend fun BankIdSeEndpoint.waitForSignAsync(id: String, opts: PollOptions = PollOptions()): BankIdSeStatus = waitForSign(id, opts).await()
suspend fun BankIdSeEndpoint.waitForAgeVerificationAsync(id: String, opts: PollOptions = PollOptions()): AgeVerificationStatus = waitForAgeVerification(id, opts).await()

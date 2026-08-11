package se.idkollen.client;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.jspecify.annotations.Nullable;

/** Verifies the {@code X-Signature-SHA256} header sent with callback requests. */
public final class CallbackSignature {
    private CallbackSignature() {}

    /**
     * Verify a callback signature.
     *
     * @param body Raw request body as received. Verify before parsing; never re-serialize.
     * @param signature Value of the {@code X-Signature-SHA256} header, or {@code null} if absent.
     * @param secret The account's HMAC secret.
     * @return {@code false} for a missing, malformed or mismatching signature.
     */
    public static boolean verify(byte[] body, @Nullable String signature, String secret) {
        if (signature == null) {
            return false;
        }

        byte[] expected;
        try {
            expected = Base64.getDecoder().decode(signature);
        } catch (IllegalArgumentException e) {
            return false;
        }

        byte[] actual;
        try {
            var mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            actual = mac.doFinal(body);
        } catch (GeneralSecurityException | IllegalArgumentException e) {
            return false;
        }

        return MessageDigest.isEqual(actual, expected);
    }
}

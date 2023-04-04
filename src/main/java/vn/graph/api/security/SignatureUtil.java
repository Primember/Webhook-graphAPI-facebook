package vn.graph.api.security;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public class SignatureUtil {

    public static boolean verifySignature(String payload, String signature, String appSecret) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(appSecret.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] signatureBytes = mac.doFinal(payload.getBytes());
            String expectedSignature = "sha256=" + Hex.encodeHexString(signatureBytes);
            return expectedSignature.equals(signature);
        } catch (Exception e) {
            return false;
        }
    }

}

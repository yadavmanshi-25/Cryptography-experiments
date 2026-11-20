import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.MGF1ParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

public class RSAExample {

    // Helper method to convert byte array to formatted hexadecimal string
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("============================================================");
        try {
            // Step 1: Generate 2048-bit RSA Key Pair
            System.out.println("[Step 1] Initializing RSA KeyPairGenerator (2048-bit)...");
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(2048);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            System.out.println("         -> Public and Private key pair generated successfully.\n");

            // Step 2: Define Original Plaintext Message
            String originalMessage = "Hello! This is an RSA encryption demo.";
            byte[] plaintextBytes = originalMessage.getBytes(StandardCharsets.UTF_8);
            System.out.println("[Step 2] Original Plaintext Message:");
            System.out.println("         \"" + originalMessage + "\"\n");

            // Step 3: Configure Cipher with OAEP (SHA-256 for main digest and MGF1 digest)
            // Explicit OAEPParameterSpec avoids provider-dependent defaults
            String transformation = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
            Cipher cipher = Cipher.getInstance(transformation);
            OAEPParameterSpec oaepSpec = new OAEPParameterSpec(
                    "SHA-256",
                    "MGF1",
                    MGF1ParameterSpec.SHA256,
                    PSource.PSpecified.DEFAULT);

            // Step 4: Encrypt using Public Key
            System.out.println("[Step 3] Encrypting message with RSA Public Key and OAEP-SHA256...");
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic(), oaepSpec);
            byte[] ciphertextBytes = cipher.doFinal(plaintextBytes);
            System.out.println("         -> Ciphertext Length: " + ciphertextBytes.length + " bytes (2048 bits)");
            System.out.println("         -> Ciphertext (Hex):");
            System.out.println("         " + bytesToHex(ciphertextBytes) + "\n");

            // Step 5: Decrypt using Private Key
            System.out.println("[Step 4] Decrypting ciphertext with RSA Private Key...");
            cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate(), oaepSpec);
            byte[] decryptedBytes = cipher.doFinal(ciphertextBytes);
            String decryptedMessage = new String(decryptedBytes, StandardCharsets.UTF_8);
            System.out.println("         -> Decrypted Plaintext Message:");
            System.out.println("         \"" + decryptedMessage + "\"\n");

            // Step 6: Verification
            System.out.println("[Step 5] Verification of Decryption Integrity:");
            boolean isMatch = originalMessage.equals(decryptedMessage);
            if (isMatch) {
                System.out.println("         [SUCCESS] Decrypted message matches the original message exactly!");
                System.out.println("         Verification Result: PASS (true)\n");
            } else {
                System.out.println("         [FAILURE] Decrypted message does NOT match original message!");
                System.out.println("         Verification Result: FAIL (false)\n");
            }
            System.out.println("         RSA EXPERIMENT COMPLETED SUCCESSFULLY              ");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("[Error] RSA cryptographic algorithm not found: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("[Error] Cryptographic operation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Scanner;

public class AES_SHA256_Application {

    private static final int AES_KEY_SIZE = 256;
    private static final int GCM_NONCE_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 128;

    // Convert byte array to hexadecimal string
    public static String toHex(byte[] data) {
        StringBuilder hex = new StringBuilder();

        for (byte b : data) {
            hex.append(String.format("%02x", b & 0xff));
        }

        return hex.toString();
    }

    // Generate AES-256 key
    public static SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(AES_KEY_SIZE, new SecureRandom());
        return keyGenerator.generateKey();
    }

    // Encrypt message using AES-GCM
    public static byte[] encrypt(
            String message,
            SecretKey key,
            byte[] nonce) throws Exception {

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, nonce);

        cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);

        return cipher.doFinal(
                message.getBytes(StandardCharsets.UTF_8));
    }

    // Decrypt message using AES-GCM
    public static String decrypt(
            byte[] ciphertext,
            SecretKey key,
            byte[] nonce) throws Exception {

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, nonce);

        cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);

        byte[] decryptedBytes = cipher.doFinal(ciphertext);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    // Generate SHA-256 hash
    public static String generateSHA256(String message)
            throws Exception {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        byte[] hash = digest.digest(
                message.getBytes(StandardCharsets.UTF_8));

        return toHex(hash);
    }

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println(" AES AND SHA-256 TEXT APPLICATION");
        System.out.println("====================================");

        try (Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter a text message: ");
            String originalMessage = scanner.nextLine();

            // Generate AES key
            SecretKey aesKey = generateAESKey();

            // Generate a fresh 12-byte nonce
            byte[] nonce = new byte[GCM_NONCE_LENGTH];
            new SecureRandom().nextBytes(nonce);

            // Encrypt the message
            byte[] encryptedMessage = encrypt(originalMessage, aesKey, nonce);

            // Decrypt the message
            String decryptedMessage = decrypt(encryptedMessage, aesKey, nonce);

            // Generate SHA-256 hash
            String hash = generateSHA256(originalMessage);

            // Display results
            System.out.println("\n========== RESULTS ==========");

            System.out.println("Original Message: "
                    + originalMessage);

            System.out.println("AES Algorithm: AES-256-GCM");

            System.out.println("Encrypted Ciphertext: "
                    + toHex(encryptedMessage));

            System.out.println("Nonce: " + toHex(nonce));

            System.out.println("Decrypted Message: "
                    + decryptedMessage);

            System.out.println("SHA-256 Hash: " + hash);

            System.out.println("Decryption Successful: "
                    + originalMessage.equals(decryptedMessage));

            System.out.println("\nExperiment Completed Successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;

public class PasswordHashing {

    // Convert byte array into hexadecimal string
    public static String toHex(byte[] data) {
        StringBuilder hex = new StringBuilder();

        for (byte b : data) {
            hex.append(String.format("%02x", b & 0xff));
        }

        return hex.toString();
    }

    // Generate SHA-256 hash
    public static byte[] generateHash(byte[] data) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(data);
    }

    // Generate a random salt
    public static byte[] generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println(" PASSWORD HASHING WITH AND WITHOUT SALT");
        System.out.println("========================================");

        try (Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            // Hash password without salt
            byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);

            byte[] unsaltedHash = generateHash(passwordBytes);

            // Generate random salt
            byte[] salt = generateSalt();

            // Combine salt and password
            byte[] saltedPassword = new byte[salt.length + passwordBytes.length];

            System.arraycopy(
                    salt, 0, saltedPassword, 0, salt.length);

            System.arraycopy(
                    passwordBytes, 0,
                    saltedPassword, salt.length,
                    passwordBytes.length);

            // Hash password with salt
            byte[] saltedHash = generateHash(saltedPassword);

            // Verify password using the same salt
            byte[] verificationData = new byte[salt.length + passwordBytes.length];

            System.arraycopy(
                    salt, 0, verificationData, 0, salt.length);

            System.arraycopy(
                    passwordBytes, 0,
                    verificationData, salt.length,
                    passwordBytes.length);

            byte[] verificationHash = generateHash(verificationData);

            boolean verified = MessageDigest.isEqual(saltedHash, verificationHash);

            // Display results
            System.out.println("\n========== RESULTS ==========");

            System.out.println("Original Password: " + password);

            System.out.println(
                    "SHA-256 Hash Without Salt: "
                            + toHex(unsaltedHash));

            System.out.println("Generated Salt: " + toHex(salt));

            System.out.println(
                    "SHA-256 Hash With Salt: "
                            + toHex(saltedHash));

            System.out.println(
                    "Password Verification: "
                            + (verified ? "SUCCESSFUL" : "FAILED"));

            System.out.println(
                    "Hash Length: " + unsaltedHash.length + " bytes");

            System.out.println("\nExperiment Completed Successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.KeyAgreement;
import javax.crypto.Mac;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DiffieHellmanExample {

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public static byte[] hkdfExtract(byte[] salt, byte[] ikm) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        if (salt == null || salt.length == 0) {
            // SHA-256 output length is 32 bytes (256 bits)
            salt = new byte[32];
        }
        mac.init(new SecretKeySpec(salt, "HmacSHA256"));
        return mac.doFinal(ikm);
    }

    public static byte[] hkdfExpand(byte[] prk, byte[] info, int outputLengthBytes) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(prk, "HmacSHA256"));
        ByteArrayOutputStream okm = new ByteArrayOutputStream();
        byte[] t = new byte[0];
        int hashLen = 32; // SHA-256 length
        int iterations = (int) Math.ceil((double) outputLengthBytes / hashLen);

        for (int i = 1; i <= iterations; i++) {
            mac.reset();
            mac.update(t);
            if (info != null && info.length > 0) {
                mac.update(info);
            }
            mac.update((byte) i);
            t = mac.doFinal();
            okm.write(t);
        }

        byte[] result = okm.toByteArray();
        return Arrays.copyOfRange(result, 0, outputLengthBytes);
    }

    public static byte[] derive256BitAesKey(byte[] rawSharedSecret, String contextInfo) throws Exception {
        byte[] info = contextInfo.getBytes(StandardCharsets.UTF_8);
        byte[] prk = hkdfExtract(null, rawSharedSecret);
        return hkdfExpand(prk, info, 32); // 32 bytes = 256 bits
    }

    public static void main(String[] args) {
        System.out.println("============================================================");

        try {
            // Step 1: Generate 2048-bit Diffie-Hellman Parameters (p and g)
            System.out.println("[Step 1] Generating 2048-bit Diffie-Hellman domain parameters (p, g)...");
            System.out.println("         (This may take a few seconds to compute prime modulus)");
            AlgorithmParameterGenerator paramGen = AlgorithmParameterGenerator.getInstance("DH");
            paramGen.init(2048, new SecureRandom());
            AlgorithmParameters params = paramGen.generateParameters();
            DHParameterSpec dhSpec = params.getParameterSpec(DHParameterSpec.class);
            System.out.println("         -> DH Parameters generated.");
            System.out.println("         -> Prime modulus (p) bit length: " + dhSpec.getP().bitLength() + " bits");
            System.out.println("         -> Base generator (g) bit length: " + dhSpec.getG().bitLength() + " bits\n");

            // Step 2: Initialize KeyPairGenerator with common parameters
            KeyPairGenerator dhKeyPairGen = KeyPairGenerator.getInstance("DH");
            dhKeyPairGen.initialize(dhSpec);

            // Step 3: Alice generates her private and public key pair
            System.out.println("[Step 2] Alice generates her DH key pair...");
            KeyPair aliceKeyPair = dhKeyPairGen.generateKeyPair();
            System.out.println("         -> Alice's public key generated: "
                    + aliceKeyPair.getPublic().getAlgorithm() + " (" + dhSpec.getP().bitLength() + "-bit)\n");

            // Step 4: Bob generates his private and public key pair
            System.out.println("[Step 3] Bob generates his DH key pair...");
            KeyPair bobKeyPair = dhKeyPairGen.generateKeyPair();
            System.out.println("         -> Bob's public key generated: "
                    + bobKeyPair.getPublic().getAlgorithm() + " (" + dhSpec.getP().bitLength() + "-bit)\n");

            // Step 5: Key Exchange and Shared Secret Computation
            System.out.println("[Step 4] Executing Key Agreement Phase over insecure channel...");

            // Alice receives Bob's public key and computes shared secret
            KeyAgreement aliceAgreement = KeyAgreement.getInstance("DH");
            aliceAgreement.init(aliceKeyPair.getPrivate());
            aliceAgreement.doPhase(bobKeyPair.getPublic(), true);
            byte[] aliceRawSecret = aliceAgreement.generateSecret();
            System.out.println("         -> Alice calculated raw shared secret (" + aliceRawSecret.length + " bytes)");

            KeyAgreement bobAgreement = KeyAgreement.getInstance("DH");
            bobAgreement.init(bobKeyPair.getPrivate());
            bobAgreement.doPhase(aliceKeyPair.getPublic(), true);
            byte[] bobRawSecret = bobAgreement.generateSecret();
            System.out.println("         -> Bob calculated raw shared secret (" + bobRawSecret.length + " bytes)\n");

            System.out.println("[Step 5] Deriving 256-bit AES symmetric keys using HKDF-SHA256...");
            String kdfContext = "CSL0509-Lab-Exp07-DH-AES-KeyDerivation";
            byte[] aliceDerivedAesKey = derive256BitAesKey(aliceRawSecret, kdfContext);
            byte[] bobDerivedAesKey = derive256BitAesKey(bobRawSecret, kdfContext);

            System.out.println("         -> Alice's Derived AES Key (Hex):");
            System.out.println("            " + bytesToHex(aliceDerivedAesKey));
            System.out.println("         -> Bob's Derived AES Key (Hex):");
            System.out.println("            " + bytesToHex(bobDerivedAesKey) + "\n");

            // Step 7: Verification
            System.out.println("[Step 6] Verifying whether both independently derived keys match:");
            boolean keysMatch = Arrays.equals(aliceDerivedAesKey, bobDerivedAesKey);
            System.out.println("         Shared keys match: " + keysMatch + "\n");

            if (keysMatch) {
                System.out
                        .println("         [SUCCESS] Alice and Bob independently derived identical 256-bit AES keys!");
                System.out.println("         Secure channel establishment is complete.\n");
            } else {
                System.out.println("         [FAILURE] Derived keys do NOT match! Key exchange failed.\n");
            }

            System.out.println("   DIFFIE-HELLMAN EXPERIMENT COMPLETED SUCCESSFULLY         ");

        } catch (Exception e) {
            System.err.println("[Error] Diffie-Hellman key exchange failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

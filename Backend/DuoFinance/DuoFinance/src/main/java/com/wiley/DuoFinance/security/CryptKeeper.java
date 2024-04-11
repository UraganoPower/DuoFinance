package com.wiley.DuoFinance.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CryptKeeper {

    private static final String ALGORITHM = "AES";
    private static String key = "MySecretKey12345";

    public static String encrypt(String value) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(value.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedValue) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
        return new String(decryptedBytes);
    }
}

/*

        String originalValue = "sensitive_data";

        // Encrypt the value
        String encryptedValue = CryptKeeper.encrypt(originalValue);
        System.out.println("Encrypted value: " + encryptedValue);

        // Decrypt the value
        String decryptedValue = CryptKeeper.decrypt(encryptedValue);
        System.out.println("Decrypted value: " + decryptedValue);

 */

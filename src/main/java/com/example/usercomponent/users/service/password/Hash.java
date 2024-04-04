package com.example.usercomponent.users.service.password;

import com.example.usercomponent.users.service.password.PasswordHasher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class Hash implements PasswordHasher {

    private static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return new BigInteger(1, salt).toString(16);
    }

    private static String hashPassword(String password, String salt) {
        String hashedPassword = null;
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hashedPassword = new BigInteger(1, factory.generateSecret(spec).getEncoded()).toString(16);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }

    @Override
    public String hashPassword(String password) {
        String salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);
        return salt + hashedPassword;
    }

    @Override
    public boolean validatePassword(String password, String hashedPassword) {
        String salt = hashedPassword.substring(0, 32);
        String hashedAttempt = hashPassword(password, salt);
        return hashedAttempt.equals(hashedPassword.substring(32));
    }
}

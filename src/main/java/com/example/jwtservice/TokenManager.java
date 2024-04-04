package com.example.jwtservice;

import com.example.usercomponent.users.models.User;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class TokenManager {
    private PublicKey publicKey;
    private PublicKey refreshTokenPublicKey;
    private PrivateKey privateKey;
    private PrivateKey refreshTokenPrivateKey;

    // Constructor to initialize keys
    public TokenManager() {
        // Load public keys
        publicKey = loadPublicKey("publicKey.pem");
        refreshTokenPublicKey = loadPublicKey("publicKeyRT.pem");

        // Load private keys
        privateKey = loadPrivateKey("privateKey.pem");
        refreshTokenPrivateKey = loadPrivateKey("privateKeyRT.pem");
    }

    public String generateAccessToken(User user) {
        // Use access token private key for signing
        return signToken(user, (RSAPrivateKey) privateKey);
    }

    public String generateRefreshToken(User user) {
        // Use refresh token private key for signing
        return signToken(user, (RSAPrivateKey) refreshTokenPrivateKey);
    }

    public boolean verifyAccessToken(String token) {
        // Use access token public key for verification
        return verifyToken(token, (RSAPublicKey) publicKey);
    }

    public boolean verifyRefreshToken(String token) {
        // Use refresh token public key for verification
        return verifyToken(token, (RSAPublicKey) refreshTokenPublicKey);
    }

    private String signToken(User user, RSAPrivateKey key) {
        // Implement token signing using the provided private key
        // Return signed token
        return ""; // Placeholder
    }

    private boolean verifyToken(String token, RSAPublicKey key) {
        // Implement token verification using the provided public key
        // Return true if verification succeeds, false otherwise
        return false; // Placeholder
    }

    private PublicKey loadPublicKey(String publicKeyLocation) {
        try {
            FileInputStream fis = new FileInputStream(publicKeyLocation);
            byte[] keyBytes = new byte[fis.available()];
            fis.read(keyBytes);
            fis.close();

            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }

    private PrivateKey loadPrivateKey(String privateKeyLocation) {
        try {
            FileInputStream fis = new FileInputStream(privateKeyLocation);
            byte[] keyBytes = new byte[fis.available()];
            fis.read(keyBytes);
            fis.close();

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }
}

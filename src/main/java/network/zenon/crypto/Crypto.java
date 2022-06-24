package network.zenon.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypto {
    public static byte[] getPublicKey(byte[] privateKey) {
        throw new UnsupportedOperationException();
        // return Ed25519.PublicKeyFromSeed(privateKey);
    }

    public static byte[] sign(byte[] message, byte[] privateKey, byte[] publicKey) {
        throw new UnsupportedOperationException();
        // var expKey = Ed25519.ExpandedPrivateKeyFromSeed(privateKey == null ?
        // publicKey : privateKey);
        // return Ed25519.Sign(message, expKey);
    }

    public static boolean verify(byte[] signature, byte[] message, byte[] publicKey) {
        throw new UnsupportedOperationException();
        // return Ed25519.Verify(signature, message, signature);
    }

    public static byte[] deriveKey(String path, byte[] seed) {
        throw new UnsupportedOperationException();
        // return Ed25519HdKey.DerivePath(path, seed).Key!;
    }

    public static byte[] digest(byte[] data) {
        return digest(data, 32);
    }

    public static byte[] digest(byte[] data, int digestSize) {
        try {
            return MessageDigest.getInstance("SHA3-256").digest(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
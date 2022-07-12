package network.zenon.crypto;

import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypto {
    public static byte[] getPublicKey(byte[] privateKey) {
        return Ed25519.publicKeyFromSeed(privateKey);
    }

    public static byte[] sign(byte[] message, byte[] privateKey, byte[] publicKey) {

        KeyPair keyPair = Ed25519.keyPairFromSeed(privateKey == null ? publicKey : privateKey);

        return Ed25519.sign(message, keyPair.getPrivate());
    }

    public static boolean verify(byte[] signature, byte[] message, byte[] publicKey) {
        return Ed25519.verify(signature, message, publicKey);
    }

    public static byte[] deriveKey(String path, byte[] seed) {
        return Ed25519HdKey.derivePath(path, seed).key;
    }

    public static byte[] digest(byte[] data) {
        return digest(data, 32);
    }

    public static byte[] digest(byte[] data, int digestSize) {
        try {
            if (digestSize == 32)
                return MessageDigest.getInstance("SHA3-256").digest(data);
            throw new NoSuchAlgorithmException();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Couldn't find a SHA3-256 provider", e);
        }
    }
}
package network.zenon.crypto;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.SignatureException;

import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;
import net.i2p.crypto.eddsa.spec.EdDSAPrivateKeySpec;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;

public class Ed25519 {
    public static final int PUBLIC_KEY_SIZE_IN_BYTES = 32;

    public static final int SIGNATURE_SIZE_IN_BYTES = 64;

    public static final int EXPANDED_PRIVATE_KEY_SIZE_IN_BYTES = 64;

    public static final int PRIVATE_KEY_SIZE_IN_BYTES = 32;

    public static final int SHARED_KEY_SIZE_IN_BYTES = 32;

    private static final EdDSAParameterSpec ED25519SPEC = EdDSANamedCurveTable.ED_25519_CURVE_SPEC;

    public static byte[] publicKeyFromSeed(byte[] privateKey) {
        java.security.KeyPair kp = keyPairFromSeed(privateKey);
        return ((EdDSAPublicKey) kp.getPublic()).getAbyte();
    }

    public static byte[] expandedPrivateKeyFromSeed(byte[] seed) {
        java.security.KeyPair kp = keyPairFromSeed(seed);
        return kp.getPrivate().getEncoded();
    }

    public static java.security.KeyPair keyPairFromSeed(byte[] privateKey) {
        EdDSAPrivateKeySpec privKey = new EdDSAPrivateKeySpec(privateKey, ED25519SPEC);
        EdDSAPublicKeySpec pubKey = new EdDSAPublicKeySpec(privKey.getA(), ED25519SPEC);

        return new java.security.KeyPair(new EdDSAPublicKey(pubKey), new EdDSAPrivateKey(privKey));
    }

    public static void sign(byte[] signature, byte[] message, PrivateKey privateKey) {
        if (signature == null) {
            throw new IllegalArgumentException("signature is null");
        }

        if (signature.length != SIGNATURE_SIZE_IN_BYTES) {
            throw new IllegalArgumentException(String.format("signature size must be %s", SIGNATURE_SIZE_IN_BYTES));
        }
        /*
         * if (expandedPrivateKey == null) { throw new
         * IllegalArgumentException("expandedPrivateKey is null"); }
         *
         * if (expandedPrivateKey.length != EXPANDED_PRIVATE_KEY_SIZE_IN_BYTES) { throw
         * new
         * IllegalArgumentException(String.format("expandedPrivateKey size must be %s",
         * EXPANDED_PRIVATE_KEY_SIZE_IN_BYTES)); }
         */
        if (message == null) {
            throw new IllegalArgumentException("message is null");
        }

        // EdDSAPrivateKey privKey = new EdDSAPrivateKey(new
        // EdDSAPrivateKeySpec(expandedPrivateKey, ED25519SPEC));

        try {
            EdDSAEngine engine = new EdDSAEngine();
            engine.initSign(privateKey);
            engine.update(message);
            engine.sign(signature, 0, signature.length);
        } catch (SignatureException e) {
            throw new IllegalArgumentException("Invalid signature");
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException("Invalid key");
        }
    }

    public static byte[] sign(byte[] message, PrivateKey privateKey) {
        byte[] array = new byte[SIGNATURE_SIZE_IN_BYTES];
        sign(array, message, privateKey);
        return array;
    }

    public static boolean verify(byte[] signature, byte[] message, byte[] publicKey) {
        if (signature == null) {
            throw new IllegalArgumentException("signature is null");
        }

        if (message == null) {
            throw new IllegalArgumentException("message is null");
        }

        if (publicKey == null) {
            throw new IllegalArgumentException("publicKey is null");
        }

        if (signature.length != SIGNATURE_SIZE_IN_BYTES) {
            throw new IllegalArgumentException(
                    String.format("Signature size must be %s bytes", SIGNATURE_SIZE_IN_BYTES));
        }

        if (publicKey.length != PUBLIC_KEY_SIZE_IN_BYTES) {
            throw new IllegalArgumentException(
                    String.format("Public key size must be %s bytes", PUBLIC_KEY_SIZE_IN_BYTES));
        }

        EdDSAPublicKey pubKey = new EdDSAPublicKey(new EdDSAPublicKeySpec(publicKey, ED25519SPEC));

        try {
            EdDSAEngine engine = new EdDSAEngine();
            engine.initVerify(pubKey);
            engine.update(message);
            return engine.verify(signature);
        } catch (SignatureException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException();
        }
    }
}

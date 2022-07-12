package network.zenon.crypto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import network.zenon.utils.ArrayUtils;
import network.zenon.utils.BytesUtils;

public class Ed25519HdKey {
    private static final String HMAC_SHA512 = "HmacSHA512";

    public static class HdKey {
        public byte[] key;
        public byte[] chainCode;
    }

    private static byte[] hmac512(byte[] key, byte[] seed) {
        try {
            Mac sha512_HMAC = Mac.getInstance(HMAC_SHA512);
            SecretKeySpec keySpec = new SecretKeySpec(seed, HMAC_SHA512);
            sha512_HMAC.init(keySpec);
            return sha512_HMAC.doFinal(key);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new UnsupportedOperationException("HMAC-SHA512 Encryption is not supported on this platform.");
        }
    }

    private static HdKey getMasterKeyFromSeed(byte[] seed) {
        byte[] hash = hmac512(seed, "ed25519 seed".getBytes());
        HdKey key = new HdKey();
        key.key = Arrays.copyOfRange(hash, 0, 32);
        key.chainCode = Arrays.copyOfRange(hash, 32, 64);

        return key;
    }

    private static HdKey derive(HdKey parent, int index) {
        byte[] buffer = ArrayUtils.concat(new byte[] { 0 }, parent.key, BytesUtils.getBytes(index));
        byte[] hash = hmac512(buffer, parent.chainCode);

        HdKey key = new HdKey();
        key.key = Arrays.copyOfRange(hash, 0, 32);
        key.chainCode = Arrays.copyOfRange(hash, 32, 64);

        return key;
    }

    public static HdKey derivePath(String path, byte[] seed) {
        HdKey masterKeyFromSeed = getMasterKeyFromSeed(seed);

        String[] span = path.split("/");
        String[] array = Arrays.copyOfRange(span, 1, span.length);

        int[] list = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            String value = array[i].replace("'", "");
            list[i] = Integer.parseInt(value);
        }

        HdKey hdKey = masterKeyFromSeed;
        for (int item : list) {
            hdKey = derive(hdKey, (item + 0x80000000));
        }

        return hdKey;
    }
}

package network.zenon.pow;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import network.zenon.model.primitives.Hash;
import network.zenon.utils.BytesUtils;

public class PoW {
    private static final int OUT_SIZE = 8;
    private static final int IN_SIZE = 32;
    private static final int DATA_SIZE = 40;

    private static final MessageDigest SHA_ALG;

    static {
        try {
            SHA_ALG = MessageDigest.getInstance("SHA3-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Couldn't find a SHA3-256 provider", e);
        }
    }

    public static String generate(Hash hash, long difficulty) {
        return BytesUtils.toHexString(generateInternal(hash.getBytes(), difficulty));
    }

    public static String benchmark(long difficulty) {
        return BytesUtils.toHexString(benchmarkInternal(difficulty));
    }

    private static byte[] generateInternal(byte[] hash, long difficulty) {
        byte[] target = getTarget(difficulty);
        byte[] entropy = getRandomSeed();
        byte[] data = getData(entropy, hash);
        byte[] h = new byte[OUT_SIZE];
        while (true) {
            hash(h, data);

            if (greater(h, target)) {
                return dataToNonce(data);
            }

            if (!nextData(data, entropy.length)) {
                data = getData(getRandomSeed(), hash);
            }
        }
    }

    private static byte[] benchmarkInternal(long difficulty) {
        byte[] target = getTarget(difficulty);
        byte[] data = getData(new byte[OUT_SIZE], new byte[IN_SIZE]);
        byte[] h = new byte[OUT_SIZE];
        while (true) {
            hash(h, data);

            if (greater(h, target)) {
                return dataToNonce(data);
            }

            if (!nextData(data, OUT_SIZE)) {
                data = new byte[OUT_SIZE];
            }
        }
    }

    private static void hash(byte[] hash, byte[] data) {
        SHA_ALG.reset();
        byte[] digest = SHA_ALG.digest(data);
        System.arraycopy(digest, 0, hash, 0, 8);
    }

    private static byte[] getTarget(long difficulty) {
        // set big to 1 << 64
        BigInteger big = BigInteger.valueOf(1L << 62);
        big = big.multiply(BigInteger.valueOf(4));

        BigInteger x = big.divide(BigInteger.valueOf(difficulty));
        x = big.min(x);

        byte[] h = new byte[OUT_SIZE];
        // set little ending encoding
        for (int i = 0; i < 8; i += 1) {
            h[i] = x.shiftRight(i * 8).byteValue();
        }
        return h;
    }

    private static boolean nextData(byte[] data, int max_size) {
        for (int i = 0; i < max_size; i += 1) {
            data[i] += 1;
            if (data[i] != 0) {
                return true;
            }
        }
        return false;
    }

    private static boolean greater(byte[] a, byte[] b) {
        for (int i = 7; i >= 0; i -= 1) {
            if (a[i] == b[i]) {
                continue;
            }
            return a[i] > b[i];
        }
        return true;
    }

    private static byte[] getRandomSeed() {
        return new SecureRandom().generateSeed(OUT_SIZE);
    }

    private static byte[] getData(byte[] entropy, byte[] hash) {
        byte[] data = new byte[DATA_SIZE];

        for (int i = 0; i < entropy.length; i += 1) {
            data[i] = entropy[i];
        }

        for (int i = 0; i < hash.length; i += 1) {
            data[i + entropy.length] = hash[i];
        }

        return data;
    }

    private static byte[] dataToNonce(byte[] data) {
        byte[] hash = new byte[8];
        for (int i = 0; i < hash.length; i += 1) {
            hash[i] = data[i];
        }
        return hash;
    }
}

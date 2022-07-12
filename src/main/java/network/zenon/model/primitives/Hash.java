package network.zenon.model.primitives;

import org.spongycastle.util.Arrays;

import network.zenon.crypto.Crypto;
import network.zenon.utils.BytesUtils;

public class Hash implements Comparable<Hash> {
    public static final int LENGTH = 32;

    public static final Hash EMPTY = parse("0000000000000000000000000000000000000000000000000000000000000000");

    public static Hash fromBytes(byte[] byteArray) {
        if (byteArray.length != LENGTH)
            throw new IllegalArgumentException("Invalid hash length");

        return new Hash(byteArray);
    }

    public static Hash parse(String hashString) {
        if (hashString.length() != 2 * LENGTH)
            throw new IllegalArgumentException("Invalid hash length");

        return new Hash(BytesUtils.fromHexString(hashString));
    }

    public static Hash digest(byte[] byteArray) {
        return new Hash(Crypto.digest(byteArray, LENGTH));
    }

    private final byte[] hash;

    private Hash(byte[] bytes) {
        this.hash = bytes;
    }

    public byte[] getBytes() {
        return this.hash;
    }

    @Override
    public String toString() {
        return BytesUtils.toHexString(this.hash);
    }

    public String toShortString() {
        String longString = this.toString();
        return longString.substring(0, 6) + "..." + longString.substring(longString.length() - 6);
    }

    @Override
    public boolean equals(Object obj) {
        return this.equals((Hash) obj);
    }

    public boolean equals(Hash other) {
        if (other == null)
            return false;

        if (this == other)
            return true;

        return Arrays.areEqual(this.hash, other.hash);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public int compareTo(Hash other) {
        return this.toString().compareTo(other.toString());
    }
}

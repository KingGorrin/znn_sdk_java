package network.zenon.utils;

import java.math.BigInteger;
import java.util.Arrays;

import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

public class BytesUtils {

    public static BigInteger decodeBigInt(byte[] bytes) {
        return new BigInteger(bytes);
    }

    public static byte[] encodeBigInt(BigInteger value) {
        return value.toByteArray();
    }

    public static byte[] bigIntToBytes(BigInteger b, int numBytes) {
        byte[] bytes = new byte[numBytes];
        byte[] biBytes = encodeBigInt(b);
        int start = (biBytes.length == numBytes + 1) ? 1 : 0;
        int length = Math.min(biBytes.length, numBytes);
        System.arraycopy(biBytes, start, bytes, numBytes - length, length);
        return bytes;
    }

    public static byte[] bigIntToBytesSigned(BigInteger b, int numBytes) {
        byte[] bytes = new byte[numBytes];
        if (b.signum() < 0)
            Arrays.fill(bytes, (byte) 0xFF);
        byte[] biBytes = encodeBigInt(b);
        int start = (biBytes.length == numBytes + 1) ? 1 : 0;
        int length = Math.min(biBytes.length, numBytes);
        System.arraycopy(biBytes, start, bytes, numBytes - length, length);
        return bytes;
    }

    public static BigInteger bytesToBigInt(byte[] bb) {
        return (bb.length == 0) ? BigInteger.ZERO : decodeBigInt(bb);
    }

    public static byte[] leftPadBytes(byte[] bytes, int size) {
        if (bytes.length >= size) {
            return bytes;
        }
        byte[] result = new byte[size];
        System.arraycopy(bytes, 0, result, size - bytes.length, bytes.length);
        return result;
    }

    public static byte[] getBytes(int value) {
        byte[] result = new byte[Integer.BYTES];
        for (int i = Integer.BYTES - 1; i >= 0; i--) {
            result[i] = (byte) (value & 0xFF);
            value >>= Byte.SIZE;
        }
        return result;
    }

    public static byte[] getBytes(long value) {
        byte[] result = new byte[Long.BYTES];
        for (int i = Long.BYTES - 1; i >= 0; i--) {
            result[i] = (byte) (value & 0xFF);
            value >>= Byte.SIZE;
        }
        return result;
    }

    public static String toBase64String(byte[] data) {
        return Base64.toBase64String(data);
    }

    public static byte[] fromBase64String(String data) {
        return Base64.decode(data);
    }

    public static String toHexString(byte[] data) {
        return Hex.toHexString(data);
    }

    public static byte[] fromHexString(String data) {
        return Hex.decode(data);
    }
}

package network.zenon.utils;

import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

public class BytesUtils {
	
	public static byte[] getBytes(int value) {
		byte[] result = new byte[Integer.BYTES];
        for (int i = Integer.BYTES - 1; i >= 0; i--) {
            result[i] = (byte)(value & 0xFF);
            value >>= Byte.SIZE;
        }
        return result;
    }

    public static byte[] getBytes(long value) {
    	byte[] result = new byte[Long.BYTES];
        for (int i = Long.BYTES - 1; i >= 0; i--) {
            result[i] = (byte)(value & 0xFF);
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

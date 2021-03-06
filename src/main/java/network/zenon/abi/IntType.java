package network.zenon.abi;

import java.math.BigInteger;

import network.zenon.utils.ArrayUtils;
import network.zenon.utils.BytesUtils;

public class IntType extends NumericType {
    public static byte[] encodeInt(long i) {
        return encodeIntBig(BigInteger.valueOf(i));
    }

    public static byte[] encodeIntBig(BigInteger bigInt) {
        return BytesUtils.bigIntToBytesSigned(bigInt, INT32_SIZE);
    }

    public static BigInteger decodeInt(byte[] encoded, int offset) {
        return BytesUtils.decodeBigInt(ArrayUtils.sublist(encoded, offset, offset + INT32_SIZE));
    }

    public IntType(String name) {
        super(name);
    }

    @Override
    public String getCanonicalName() {
        return this.getName().equals("int") ? "int256" : super.getCanonicalName();
    }

    @Override
    public byte[] encode(Object value) {
        return encodeIntBig(this.encodeInternal(value));
    }

    @Override
    public Object decode(byte[] encoded, int offset) {
        return decodeInt(encoded, offset);
    }
}

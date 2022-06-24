package network.zenon.abi;

import java.math.BigInteger;

import network.zenon.utils.ArrayUtils;
import network.zenon.utils.BytesUtils;

public class UnsignedIntType extends NumericType {
    public static byte[] encodeInt(long i) {
        return encodeIntBig(BigInteger.valueOf(i));
    }

    public static byte[] encodeIntBig(BigInteger bigInt) {
        if (bigInt.signum() == -1)
            throw new IllegalArgumentException("Value must be unsigned");

        return BytesUtils.bigIntToBytes(bigInt, INT32_SIZE);
    }

    public static BigInteger decodeInt(byte[] encoded, int offset) {
        return BytesUtils.decodeBigInt(ArrayUtils.sublist(encoded, offset, offset + INT32_SIZE));
    }

    public UnsignedIntType(String name) {
        super(name);
    }

    @Override
    public String getCanonicalName() {
        return this.getName().equals("uint") ? "uint256" : super.getCanonicalName();
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
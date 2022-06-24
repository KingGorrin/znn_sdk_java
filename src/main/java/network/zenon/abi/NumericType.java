package network.zenon.abi;

import java.math.BigDecimal;
import java.math.BigInteger;

import network.zenon.utils.BytesUtils;

public abstract class NumericType extends AbiType {
    public static BigInteger toBigInt(Object value) {
        if (value instanceof String) {
            String str = (String) value;

            if (str.startsWith("0x")) {
                return new BigInteger(str.substring(2), 16);
            } else if (str.indexOf('a') != -1 || str.indexOf('b') != -1 || str.indexOf('c') != -1 || str.indexOf('d') != -1
                    || str.indexOf('e') != -1 || str.indexOf('f') != -1) {
                return new BigInteger(str, 16);
            }
            return new BigInteger(str);
        } else if (value instanceof BigInteger) {
            return (BigInteger) value;
        } else if (value instanceof Byte) {
            return BigInteger.valueOf((Byte) value);
        } else if (value instanceof Short) {
            return BigInteger.valueOf((Short) value);
        } else if (value instanceof Integer) {
            return BigInteger.valueOf((Integer) value);
        } else if (value instanceof Long) {
            return BigInteger.valueOf((Long) value);
        } else if (value instanceof Float) {
            return BigDecimal.valueOf((Float) value).toBigInteger();
        } else if (value instanceof Double) {
            return BigDecimal.valueOf((Double) value).toBigInteger();
        } else if (value instanceof byte[]) {
            return BytesUtils.bytesToBigInt((byte[]) value);
        }

        throw new UnsupportedOperationException(
                String.format("Value type '%s' is not supported.", value.getClass().getName()));
    }

    public NumericType(String name) {
        super(name);
    }

    protected BigInteger encodeInternal(Object value) {
        return toBigInt(value);
    }
}

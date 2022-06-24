package network.zenon.abi;

import network.zenon.utils.ArrayUtils;

public class FunctionType extends Bytes32Type {
    public FunctionType() {
        super("function");
    }

    @Override
    public byte[] encode(Object value) {
        if (!(value instanceof byte[]))
            throw new IllegalArgumentException("Value must be a byte array");

        byte[] bytes = (byte[]) value;

        if (bytes.length != 24)
            throw new IllegalArgumentException("Byte array must be 24 bytes in length");

        return super.encode(ArrayUtils.concat(bytes, new byte[8]));
    }

    @Override
    public Object decode(byte[] encoded, int offset) {
        throw new UnsupportedOperationException();
    }
}

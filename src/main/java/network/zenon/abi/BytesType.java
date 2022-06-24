package network.zenon.abi;

import network.zenon.utils.ArrayUtils;
import network.zenon.utils.BytesUtils;

public class BytesType extends AbiType {
    public static final BytesType BYTES = new BytesType("bytes");

    public BytesType(String name) {
        super(name);
    }

    @Override
    public byte[] encode(Object value) {
        byte[] bytes;
        if (value instanceof byte[]) {
            bytes = (byte[]) value;
        } else if (value instanceof String) {
            bytes = BytesUtils.fromHexString((String) value);
        } else {
            throw new UnsupportedOperationException(
                    String.format("Value type '%s' is not supported.", value.getClass().getName()));
        }

        int size = this.getFixedSize();
        int resultLength = Math.round((((bytes.length - 1) / size) + 1) * size);
        byte[] result = new byte[resultLength];

        System.arraycopy(bytes, 0, result, 0, bytes.length);

        return ArrayUtils.concat(IntType.encodeInt(bytes.length), result);
    }

    @Override
    public Object decode(byte[] encoded, int offset) {
        int len = IntType.decodeInt(encoded, offset).intValue();
        if (len == 0) {
            return new byte[0];
        }
        offset += 32;
        byte[] l = new byte[len];
        System.arraycopy(encoded, offset, l, 0, len);
        return l;
    }

    @Override
    public boolean isDynamicType() {
        return true;
    }
}

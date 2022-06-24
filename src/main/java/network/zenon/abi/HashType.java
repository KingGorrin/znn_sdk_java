package network.zenon.abi;

import network.zenon.model.primitives.Hash;
import network.zenon.utils.BytesUtils;

public class HashType extends AbiType {
    public HashType(String name) {
        super(name);
    }

    @Override
    public byte[] encode(Object value) {
        if (value instanceof String) {
            byte[] result = new byte[this.getFixedSize()];
            byte[] bytes = BytesUtils.fromHexString((String) value);
            System.arraycopy(bytes, 0, result, 0, bytes.length);
            return result;
        } else if (value instanceof byte[]) {
            byte[] result = new byte[this.getFixedSize()];
            byte[] bytes = (byte[]) value;
            System.arraycopy(bytes, 0, result, 0, bytes.length);
            return result;
        } else {
            return IntType.encodeIntBig(NumericType.toBigInt(value));
        }
    }

    @Override
    public Hash decode(byte[] encoded, int offset) {
        byte[] result = new byte[this.getFixedSize()];
        System.arraycopy(encoded, offset, result, 0, result.length);
        return Hash.fromBytes(result);
    }
}

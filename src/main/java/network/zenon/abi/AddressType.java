package network.zenon.abi;

import network.zenon.model.primitives.Address;
import network.zenon.utils.BytesUtils;

public class AddressType extends IntType {
    public static final String DEFAULT_NAME = "address";

    public AddressType() {
        super(DEFAULT_NAME);
    }

    @Override
    public byte[] encode(Object value) {
        if (value instanceof String) {
            return BytesUtils.leftPadBytes(Address.parse((String) value).getBytes(), 32);
        } else if (value instanceof Address) {
            return BytesUtils.leftPadBytes(((Address) value).getBytes(), 32);
        }

        throw new UnsupportedOperationException(
                String.format("Value type '%s' is not supported.", value.getClass().getName()));
    }

    @Override
    public Object decode(byte[] encoded, int offset) {
        byte[] l = new byte[20];
        System.arraycopy(encoded, offset + 12, l, 0, 20);
        return new Address("z", l);
    }
}

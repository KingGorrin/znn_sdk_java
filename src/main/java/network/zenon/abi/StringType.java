package network.zenon.abi;

import java.nio.charset.StandardCharsets;

public class StringType extends BytesType {
    public StringType() {
        super("string");
    }

    @Override
    public byte[] encode(Object value) {
        if (value instanceof String) {
            String str = (String) value;

            return super.encode(str.getBytes(StandardCharsets.UTF_8));
        }

        throw new UnsupportedOperationException(
                String.format("Value type '%s' is not supported.", value.getClass().getName()));
    }

    @Override
    public String decode(byte[] encoded, int offset) {
        byte[] decoded = (byte[])super.decode(encoded, offset);
        return new String(decoded, StandardCharsets.UTF_8);
    }
}

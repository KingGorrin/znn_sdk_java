package network.zenon.abi;

public class BoolType extends IntType {
    public static final String DEFAULT_NAME = "bool";

    public BoolType() {
        super(DEFAULT_NAME);
    }

    @Override
    public byte[] encode(Object value) {
        if (value instanceof String) {
            return super.encode(Boolean.parseBoolean((String) value) ? 1 : 0);
        } else if (value instanceof Boolean) {
            return super.encode((Boolean) value == true ? 1 : 0);
        }

        throw new UnsupportedOperationException(
                String.format("Value type '%s' is not supported.", value.getClass().getName()));
    }

    @Override
    public Object decode(byte[] encoded, int offset) {
        return (!super.decode(encoded, offset).toString().equals("0"));
    }
}

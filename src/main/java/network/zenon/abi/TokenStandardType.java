package network.zenon.abi;

import network.zenon.model.primitives.TokenStandard;
import network.zenon.utils.BytesUtils;

public class TokenStandardType extends IntType {
    public static final String DEFAULT_NAME = "tokenStandard";

    public TokenStandardType() {
        super(DEFAULT_NAME);
    }

    @Override
    public byte[] encode(Object value) {
        if (value instanceof String) {
            return BytesUtils.leftPadBytes(TokenStandard.parse((String) value).getBytes(), INT32_SIZE);
        }
        if (value instanceof TokenStandard) {
            return BytesUtils.leftPadBytes(((TokenStandard) value).getBytes(), INT32_SIZE);
        }

        throw new UnsupportedOperationException(
                String.format("Value type '%s' is not supported.", value.getClass().getName()));
    }

    @Override
    public Object decode(byte[] encoded, int offset) {
        byte[] result = new byte[10];
        System.arraycopy(encoded, offset + 22, result, 0, 10);
        return TokenStandard.fromBytes(result);
    }
}

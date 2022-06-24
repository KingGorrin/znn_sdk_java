package network.zenon.abi;

public abstract class AbiType {
    public static final int INT32_SIZE = 32;

    public static AbiType getType(String typeName) {
        if (typeName.contains("["))
            return ArrayType.getType(typeName);
        if (typeName == "bool")
            return new BoolType();
        if (typeName.startsWith("int"))
            return new IntType(typeName);
        if (typeName.startsWith("uint"))
            return new UnsignedIntType(typeName);
        if (typeName.equals("address"))
            return new AddressType();
        if (typeName.equals("tokenStandard"))
            return new TokenStandardType();
        if (typeName.equals("string"))
            return new StringType();
        if (typeName.equals("bytes"))
            return BytesType.BYTES;
        if (typeName.equals("function"))
            return new FunctionType();
        if (typeName.equals("hash"))
            return new HashType(typeName);
        if (typeName.startsWith("bytes"))
            return new Bytes32Type(typeName);

        throw new UnsupportedOperationException(String.format("The type '%s' is not supported.", typeName));
    }

    private final String name;

    protected AbiType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getCanonicalName() {
        return this.name;
    }

    public abstract byte[] encode(Object value);

    public Object decode(byte[] encoded) {
        return decode(encoded, 0);
    }

    public abstract Object decode(byte[] encoded, int offset);

    public int getFixedSize() {
        return INT32_SIZE;
    }

    public boolean isDynamicType() {
        return false;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
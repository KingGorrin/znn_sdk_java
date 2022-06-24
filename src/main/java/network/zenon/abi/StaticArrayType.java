package network.zenon.abi;

import java.util.List;

public class StaticArrayType extends ArrayType {
    public final int size;

    public StaticArrayType(String name) {
        super(name);

        int idx1 = name.indexOf('[');
        int idx2 = name.indexOf(']', idx1);
        String dim = name.substring(idx1 + 1, idx2);
        this.size = Integer.parseInt(dim);
    }

    @Override
    public String getCanonicalName() {
        return String.format("[%s]", this.size);
    }

    @Override
    public byte[] encodeList(List<?> l) {
        if (l.size() != size)
            throw new IllegalArgumentException("Bytes size must equal.");
        return this.encodeTuple(l);
    }

    @Override
    public Object decode(byte[] encoded, int offset) {
        Object[] result = new Object[size];

        for (int i = 0; i < size; i++) {
            result[i] = this.getElementType().decode(encoded, offset + i * this.getElementType().getFixedSize());
        }

        return result;
    }

    @Override
    public int getFixedSize() {
        return this.getElementType().getFixedSize() * size;
    }
}

package network.zenon.abi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.jsoniter.any.Any;

import network.zenon.utils.ArrayUtils;
import network.zenon.utils.JsonUtils;

public abstract class ArrayType extends AbiType {
    public static ArrayType getType(String typeName) {
        int idx1 = typeName.indexOf('[');
        int idx2 = typeName.indexOf(']', idx1);
        if (idx1 + 1 == idx2) {
            return new DynamicArrayType(typeName);
        } else {
            return new StaticArrayType(typeName);
        }
    }

    private final AbiType elementType;

    public ArrayType(String name) {
        super(name);

        int idx = name.indexOf('[');
        String st = name.substring(0, idx);
        int idx2 = name.indexOf(']', idx);
        String subDim = idx2 + 1 == name.length() ? "" : name.substring(idx2 + 1);
        this.elementType = AbiType.getType(st + subDim);
    }

    public AbiType getElementType() {
        return this.elementType;
    }

    @Override
    public byte[] encode(Object value) {
        if (value instanceof byte[]) {
            byte[] items = (byte[])value;
            List<Byte> list = new ArrayList<Byte>();
            for (byte item : items) {
                list.add(item);
            }
            return encodeList(list);
        } else if (value instanceof short[]) {
            short[] items = (short[])value;
            List<Short> list = new ArrayList<Short>();
            for (short item : items) {
                list.add(item);
            }
            return encodeList(list);
        } else if (value instanceof int[]) {
            int[] items = (int[])value;
            List<Integer> list = new ArrayList<Integer>();
            for (int item : items) {
                list.add(item);
            }
            return encodeList(list);
        } else if (value instanceof long[]) {
            long[] items = (long[])value;
            List<Long> list = new ArrayList<Long>();
            for (long item : items) {
                list.add(item);
            }
            return encodeList(list);
        } else if (value instanceof float[]) {
            float[] items = (float[])value;
            List<Float> list = new ArrayList<Float>();
            for (float item : items) {
                list.add(item);
            }
            return encodeList(list);
        } else if (value instanceof double[]) {
            double[] items = (double[])value;
            List<Double> list = new ArrayList<Double>();
            for (double item : items) {
                list.add(item);
            }
            return encodeList(list);
        } else if (value.getClass().isArray()) {
            return encodeList(Arrays.asList((Object[])value));
        } else if (value instanceof String) {
            Any array = JsonUtils.deserializeAny((String)value);
            return encodeList(array.asList());
        }
        
        throw new UnsupportedOperationException(
                String.format("Value type '%s' is not supported.", value.getClass().getName()));

    }

    public abstract byte[] encodeList(List<?> l);

    public byte[] encodeTuple(List<?> l) {
        byte[][] elems;
        if (this.getElementType().isDynamicType()) {
            elems = new byte[l.size() * 2][];
            int offset = l.size() * this.getFixedSize();

            for (int i = 0; i < l.size(); i++) {
                elems[i] = IntType.encodeInt(offset);
                byte[] encoded = this.getElementType().encode(l.get(i));
                elems[l.size() + i] = encoded;
                offset += (int) (this.getFixedSize() * ((encoded.length - 1) / this.getFixedSize() + 1));
            }
        } else {
            elems = new byte[l.size()][];
            for (int i = 0; i < l.size(); i++) {
                elems[i] = this.getElementType().encode(l.get(i));
            }
        }
        return ArrayUtils.concat(elems);
    }

    @Override
    public Object decode(byte[] encoded, int offset) {
        throw new UnsupportedOperationException();
    }

    public Object decodeTuple(byte[] encoded, int origOffset, int len) {
        int offset = origOffset;
        Object[] ret = new Object[len];

        for (int i = 0; i < len; i++) {
            if (this.getElementType().isDynamicType()) {
                ret[i] = this.getElementType().decode(encoded,
                        origOffset + IntType.decodeInt(encoded, offset).intValue());
            } else {
                ret[i] = this.getElementType().decode(encoded, offset);
            }
            offset += this.getElementType().getFixedSize();
        }

        return ret;
    }
}

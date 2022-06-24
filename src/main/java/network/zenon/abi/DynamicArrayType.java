package network.zenon.abi;

import java.util.List;

import network.zenon.utils.ArrayUtils;

public class DynamicArrayType extends ArrayType {
    public DynamicArrayType(String name) {
        super(name);
    }

    @Override
    public String getCanonicalName() {
        return String.format("%s[]", this.getElementType().getCanonicalName());
    }
    
    @Override
    public byte[] encodeList(List<?> l) {
        return ArrayUtils.concat(IntType.encodeInt(l.size()), encodeTuple(l));
    }

    @Override
    public Object decode(byte[] encoded, int origOffset) {
        int len = IntType.decodeInt(encoded, origOffset).intValue();
        origOffset += 32;
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

    @Override
    public boolean isDynamicType() {
        return true;
    }
}

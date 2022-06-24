package network.zenon.abi;

import java.util.ArrayList;
import java.util.List;

public class Param {
    public static List<Object> decodeList(List<Param> parameters, byte[] encoded) {
        List<Object> result = new ArrayList<Object>();
        int offset = 0;
        for (Param param : parameters) {
            Object decoded = param.getType().isDynamicType()
                    ? param.getType().decode(encoded, IntType.decodeInt(encoded, offset).intValue())
                    : param.getType().decode(encoded, offset);
            result.add(decoded);
            offset += param.getType().getFixedSize();
        }
        return result;
    }

    private final String name;
    private final AbiType type;
    public boolean indexed = false;

    public Param(String name, AbiType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public AbiType getType() {
        return this.type;
    }
}
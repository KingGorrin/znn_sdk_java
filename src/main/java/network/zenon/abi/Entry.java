package network.zenon.abi;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import network.zenon.crypto.Crypto;
import network.zenon.utils.ArrayUtils;

public class Entry {
    private String name;
    private List<Param> inputs;
    private TypeEnum type;

    public Entry(String name, List<Param> inputs, TypeEnum type) {
        this.name = name;
        this.inputs = Collections.unmodifiableList(inputs);
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public List<Param> getInputs() {
        return this.inputs;
    }

    public TypeEnum getType() {
        return this.type;
    }

    public String formatSignature() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getName()).append("(");
        for (int i = 0; i < this.inputs.size(); i++) {
            if (i != 0)
                builder.append(",");
            builder.append(this.inputs.get(i).getType().getCanonicalName());
        }
        return builder.append(")").toString();
    }

    public byte[] fingerprintSignature() {
        return Crypto.digest(this.formatSignature().getBytes(StandardCharsets.UTF_8));
    }

    public byte[] encodeSignature() {
        return this.fingerprintSignature();
    }

    public byte[] encodeArguments(List<Object> args) {
        if (args.size() > this.inputs.size())
            throw new IllegalArgumentException("Arguments cannot be bigger than input size.");

        int staticSize = 0;
        int dynamicCnt = 0;

        for (int i = 0; i < args.size(); i++) {
            AbiType type = this.inputs.get(i).getType();
            if (type.isDynamicType()) {
                dynamicCnt++;
            }
            staticSize += type.getFixedSize();
        }

        byte[][] bb = new byte[args.size() + dynamicCnt][];

        for (int curDynamicPtr = staticSize, curDynamicCnt = 0, i = 0; i < args.size(); i++) {
            AbiType type = this.inputs.get(i).getType();
            if (type.isDynamicType()) {
                byte[] dynBB = type.encode(args.get(i));
                bb[i] = IntType.encodeInt(curDynamicPtr);
                bb[args.size() + curDynamicCnt] = dynBB;
                curDynamicCnt++;
                curDynamicPtr += dynBB.length;
            } else {
                bb[i] = type.encode(args.get(i));
            }
        }

        return ArrayUtils.concat(bb);
    }
}

package network.zenon.abi;

import java.util.List;

import network.zenon.utils.ArrayUtils;

public class AbiFunction extends Entry {
    public static final int ENCODED_SIGN_LENGTH = 4;

    public AbiFunction(String name, List<Param> inputs) {
        super(name, inputs, TypeEnum.FUNCTION);
    }

    public List<Object> decode(byte[] encoded) {
        return Param.decodeList(this.getInputs(), ArrayUtils.sublist(encoded, ENCODED_SIGN_LENGTH, encoded.length));
    }

    public byte[] encode(List<Object> args) {
        return ArrayUtils.concat(this.encodeSignature(), this.encodeArguments(args));
    }

    @Override
    public byte[] encodeSignature() {
        return extractSignature(super.encodeSignature());
    }

    public static byte[] extractSignature(byte[] data) {
        return ArrayUtils.sublist(data, 0, ENCODED_SIGN_LENGTH);
    }
}

package network.zenon.abi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import network.zenon.ZnnSdkException;
import network.zenon.abi.json.JEntry;

public class Abi {
    public static Abi parse(JEntry[] json) {
        List<Entry> entries = new ArrayList<Entry>();

        for (JEntry entry : json) {
            if (!entry.type.equals("function"))
                throw new ZnnSdkException("Only ABI functions supported");

            List<Param> inputs = entry.inputs != null
                    ? entry.inputs.stream().map(x -> new Param(x.name, AbiType.getType(x.type))).toList()
                    : List.of();

            entries.add(new AbiFunction(entry.name, inputs));
        }

        return new Abi(entries);
    }

    private final List<Entry> entires;

    public Abi(List<Entry> entries) {
        this.entires = Collections.unmodifiableList(entries);
    }

    public List<Entry> getEntries() {
        return this.entires;
    }

    public byte[] encodeFunction(String name, Object ... args) {
        return this.getEntries().stream().filter(x -> x.getName().equals(name))
                .map(x -> new AbiFunction(x.getName(), x.getInputs()))
                .findFirst()
                .orElseThrow()
                .encode(Arrays.asList(args));
    }

    public List<Object> decodeFunction(byte[] encoded) {
        return this.getEntries().stream()
                .filter(x -> Arrays.equals(AbiFunction.extractSignature(x.encodeSignature())
                        ,AbiFunction.extractSignature(encoded)))
                .map(x -> new AbiFunction(x.getName(), x.getInputs()))
                .findFirst()
                .orElseThrow()
                .decode(encoded);
    }
}

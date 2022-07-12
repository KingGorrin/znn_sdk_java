package network.zenon.abi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import network.zenon.ZnnSdkException;
import network.zenon.abi.json.JEntry;
import network.zenon.abi.json.JParam;

public class Abi {
    public static Abi parse(JEntry[] json) {
        List<Entry> entries = new ArrayList<>();

        for (JEntry entry : json) {
            if (!entry.type.equals("function"))
                throw new ZnnSdkException("Only ABI functions supported");

            List<Param> inputs = new ArrayList<>();
            if (entry.inputs != null) {
                for (JParam input : entry.inputs) {
                    inputs.add(new Param(input.name, AbiType.getType(input.type)));
                }
            }
            entries.add(new AbiFunction(entry.name, inputs));
        }

        return new Abi(entries);
    }

    private final List<Entry> entries;

    public Abi(List<Entry> entries) {
        this.entries = Collections.unmodifiableList(entries);
    }

    public List<Entry> getEntries() {
        return this.entries;
    }

    public byte[] encodeFunction(String name, Object... args) {
        List<Entry> entries = this.getEntries();
        for (Entry entry : entries) {
            if (entry.getName().equals(name)) {
                return new AbiFunction(entry.getName(), entry.getInputs()).encode(Arrays.asList(args));
            }
        }
        throw new IllegalArgumentException(String.format("Cannot find function that matches the name '%s'.", name));
    }

    public List<Object> decodeFunction(byte[] encoded) {
        byte[] signature = AbiFunction.extractSignature(encoded);
        List<Entry> entries = this.getEntries();
        for (Entry entry : entries) {
            if (Arrays.equals(AbiFunction.extractSignature(entry.encodeSignature()), signature)) {
                return new AbiFunction(entry.getName(), entry.getInputs()).decode(encoded);
            }
        }
        throw new IllegalArgumentException(
                String.format("Cannot find function that matches the signature %s.", Arrays.toString(signature)));
    }
}

package network.zenon.model.embedded;

import com.jsoniter.output.JsonStream;

import network.zenon.model.IJsonConvertible;
import network.zenon.model.embedded.json.JSwapLegacyPillarEntry;
import network.zenon.model.primitives.Hash;

public class SwapLegacyPillarEntry implements IJsonConvertible<JSwapLegacyPillarEntry> {
    private final long numPillars;
    private final Hash keyIdHash;

    public SwapLegacyPillarEntry(JSwapLegacyPillarEntry json) {
        this.numPillars = json.numPillars;
        this.keyIdHash = Hash.parse(json.keyIdHash);
    }

    public SwapLegacyPillarEntry(long numPillars, Hash keyIdHash) {
        this.numPillars = numPillars;
        this.keyIdHash = keyIdHash;
    }

    public long getNumPillars() {
        return this.numPillars;
    }

    public Hash getKeyIdHash() {
        return this.keyIdHash;
    }

    @Override
    public JSwapLegacyPillarEntry toJson() {
        JSwapLegacyPillarEntry json = new JSwapLegacyPillarEntry();
        json.numPillars = this.numPillars;
        json.keyIdHash = this.keyIdHash.toString();
        return json;
    }

    @Override
    public String toString() {
        return JsonStream.serialize(this.toJson());
    }
}
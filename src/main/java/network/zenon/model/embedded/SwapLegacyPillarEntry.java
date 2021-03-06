package network.zenon.model.embedded;

import network.zenon.model.JsonConvertible;
import network.zenon.model.embedded.json.JSwapLegacyPillarEntry;
import network.zenon.model.primitives.Hash;
import network.zenon.utils.JsonUtils;

public class SwapLegacyPillarEntry implements JsonConvertible<JSwapLegacyPillarEntry> {
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
        return JsonUtils.serialize(this.toJson());
    }
}
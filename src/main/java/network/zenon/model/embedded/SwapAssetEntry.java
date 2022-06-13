package network.zenon.model.embedded;

import com.jsoniter.output.JsonStream;

import network.zenon.model.IJsonConvertible;
import network.zenon.model.embedded.json.JSwapAssetEntry;
import network.zenon.model.primitives.Hash;

public class SwapAssetEntry implements IJsonConvertible<JSwapAssetEntry> {
    private final Hash keyIdHash;
    private final long qsr;
    private final long znn;

    public SwapAssetEntry(Hash keyIdHash, JSwapAssetEntry json) {
        this.keyIdHash = keyIdHash;
        this.qsr = json.qsr;
        this.znn = json.znn;
    }

    public SwapAssetEntry(Hash keyIdHash, long qsr, long znn) {
        this.keyIdHash = keyIdHash;
        this.qsr = qsr;
        this.znn = znn;
    }

    public Hash getKeyIdHash() {
        return this.keyIdHash;
    }

    public long getQsr() {
        return this.qsr;
    }

    public long getZnn() {
        return this.znn;
    }

    public boolean hasBalance() {
        return this.qsr > 0 || this.znn > 0;
    }

    @Override
    public JSwapAssetEntry toJson() {
        JSwapAssetEntry json = new JSwapAssetEntry();
        json.keyIdHash = this.keyIdHash.toString();
        json.qsr = this.qsr;
        json.znn = this.znn;
        return json;
    }

    @Override
    public String toString() {
        return JsonStream.serialize(this.toJson());
    }
}
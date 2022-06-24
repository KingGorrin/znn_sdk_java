package network.zenon.model.primitives;

import network.zenon.model.primitives.json.JHashHeight;
import network.zenon.utils.ArrayUtils;
import network.zenon.utils.BytesUtils;
import network.zenon.utils.JsonUtils;

public class HashHeight {
    public static final HashHeight EMPTY = new HashHeight(Hash.EMPTY, Long.valueOf(0));

    private final Hash hash;
    private final Long height;

    public HashHeight(JHashHeight json) {
        hash = Hash.parse(json.hash);
        height = json.height;
    }

    public HashHeight(Hash hash, Long height) {
        this.hash = hash;
        this.height = height;
    }

    public Hash getHash() {
        return this.hash;
    }

    public Long getHeight() {
        return this.height;
    }

    public byte[] getBytes() {
        return ArrayUtils.concat(this.hash.getBytes(), BytesUtils.getBytes(this.height));
    }

    public JHashHeight toJson() {
        JHashHeight json = new JHashHeight();
        json.hash = this.hash.toString();
        json.height = this.height;
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}
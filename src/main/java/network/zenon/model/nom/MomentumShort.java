package network.zenon.model.nom;

import com.jsoniter.output.JsonStream;

import network.zenon.model.IJsonConvertible;
import network.zenon.model.nom.json.JMomentumShort;
import network.zenon.model.primitives.Hash;

public class MomentumShort implements IJsonConvertible<JMomentumShort> {
    private final Hash hash;
    private final Long height;
    private final Long timestamp;

    public MomentumShort(JMomentumShort json) {
        this.hash = Hash.parse(json.hash);
        this.height = json.height;
        this.timestamp = json.timestamp;
    }

    public MomentumShort(Hash hash, Long height, Long timestamp) {
        this.hash = hash;
        this.height = height;
        this.timestamp = timestamp;
    }

    public Hash getHash() {
        return this.hash;
    }

    public Long getHeight() {
        return this.height;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    @Override
    public JMomentumShort toJson() {
        JMomentumShort json = new JMomentumShort();
        json.hash = this.hash.toString();
        json.height = this.height;
        json.timestamp = this.timestamp;
        return json;
    }

    @Override
    public String toString() {
        return JsonStream.serialize(this.toJson());
    }
}
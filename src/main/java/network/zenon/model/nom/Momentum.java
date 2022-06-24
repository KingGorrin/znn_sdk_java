package network.zenon.model.nom;

import java.util.Collections;
import java.util.List;

import network.zenon.model.JsonConvertible;
import network.zenon.model.nom.json.JMomentum;
import network.zenon.model.primitives.Address;
import network.zenon.model.primitives.Hash;
import network.zenon.utils.BytesUtils;
import network.zenon.utils.JsonUtils;

public class Momentum implements JsonConvertible<JMomentum> {
    private final int version;
    private final int chainIdentifier;
    private final Hash hash;
    private final Hash previousHash;
    private final long height;
    private final long timestamp;
    private final byte[] data;
    private final List<AccountHeader> content;
    private final Hash changesHash;
    private final String publicKey;
    private final String signature;
    private final Address producer;

    public Momentum(JMomentum json) {
        this.version = json.version;
        this.chainIdentifier = json.chainIdentifier;
        this.hash = Hash.parse(json.hash);
        this.previousHash = Hash.parse(json.previousHash);
        this.height = json.height;
        this.timestamp = json.timestamp;
        this.data = json.data == null || json.data.isEmpty() ? new byte[0] : BytesUtils.fromBase64String(json.data);
        this.content = Collections.unmodifiableList(json.content.stream().map(x -> new AccountHeader(x)).toList());
        this.changesHash = Hash.parse(json.changesHash);
        this.publicKey = json.publicKey == null || json.publicKey.isEmpty() ? "" : json.publicKey;
        this.signature = json.signature == null || json.signature.isEmpty() ? "" : json.signature;
        this.producer = Address.parse(json.producer);
    }

    public int getVersion() {
        return this.version;
    }

    public int getChainIdentifier() {
        return this.chainIdentifier;
    }

    public Hash getHash() {
        return this.hash;
    }

    public Hash getPreviousHash() {
        return this.previousHash;
    }

    public long getHeight() {
        return this.height;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public byte[] getData() {
        return this.data;
    }

    public List<AccountHeader> getContent() {
        return this.content;
    }

    public Hash getChangesHash() {
        return this.changesHash;
    }

    public String getPublicKey() {
        return this.publicKey;
    }

    public String getSignature() {
        return this.signature;
    }

    public Address getProducer() {
        return this.producer;
    }

    @Override
    public JMomentum toJson() {
        JMomentum json = new JMomentum();
        json.version = this.version;
        json.chainIdentifier = this.chainIdentifier;
        json.hash = this.hash.toString();
        json.previousHash = this.previousHash.toString();
        json.height = this.height;
        json.timestamp = this.timestamp;
        json.data = this.data != null && this.data.length != 0 ? BytesUtils.toBase64String(this.data) : "";
        json.content = this.content.stream().map(x -> x.toJson()).toList();
        json.changesHash = this.changesHash.toString();
        json.publicKey = this.publicKey;
        json.signature = this.signature;
        json.producer = this.producer.toString();
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}
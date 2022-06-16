package network.zenon.model.nom;

import com.jsoniter.output.JsonStream;

import network.zenon.model.JsonConvertible;
import network.zenon.model.nom.json.JAccountHeader;
import network.zenon.model.primitives.Address;
import network.zenon.model.primitives.Hash;

public class AccountHeader implements JsonConvertible<JAccountHeader> {
    private final Address address;
    private final Hash hash;
    private final Long height;

    public AccountHeader(JAccountHeader json) {
        this.address = Address.parse(json.address);
        this.hash = Hash.parse(json.hash);
        this.height = json.height;
    }

    public AccountHeader(Address address, Hash hash, Long height) {
        this.address = address;
        this.hash = hash;
        this.height = height;
    }

    /// Added here for simplicity. Is not part of the RPC response.
    public Address getAddress() {
        return this.address;
    }

    public Hash getHash() {
        return this.hash;
    }

    public Long getHeight() {
        return this.height;
    }

    @Override
    public JAccountHeader toJson() {
        JAccountHeader json = new JAccountHeader();
        json.address = this.address.toString();
        json.hash = this.hash.toString();
        json.height = this.height;
        return json;
    }

    @Override
    public String toString() {
        return JsonStream.serialize(this.toJson());
    }
}
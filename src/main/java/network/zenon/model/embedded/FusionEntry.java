package network.zenon.model.embedded;

import com.jsoniter.output.JsonStream;

import network.zenon.model.JsonConvertible;
import network.zenon.model.embedded.json.JFusionEntry;
import network.zenon.model.primitives.Address;
import network.zenon.model.primitives.Hash;

public class FusionEntry implements JsonConvertible<JFusionEntry> {
    private final long qsrAmount;
    private final Address beneficiary;
    private final long expirationHeight;
    private final Hash id;

    public FusionEntry(JFusionEntry json) {
        this.qsrAmount = json.qsrAmount;
        this.beneficiary = Address.parse(json.beneficiary);
        this.expirationHeight = json.expirationHeight;
        this.id = Hash.parse(json.id);
    }

    public FusionEntry(Address beneficiary, long expirationHeight, Hash id, long qsrAmount) {
        this.beneficiary = beneficiary;
        this.expirationHeight = expirationHeight;
        this.id = id;
        this.qsrAmount = qsrAmount;
    }

    public long getQsrAmount() {
        return this.qsrAmount;
    }

    public Address getBeneficiary() {
        return this.beneficiary;
    }

    public long getExpirationHeight() {
        return this.expirationHeight;
    }

    public Hash getId() {
        return this.id;
    }

    @Override
    public JFusionEntry toJson() {
        JFusionEntry json = new JFusionEntry();
        json.qsrAmount = this.qsrAmount;
        json.beneficiary = this.beneficiary.toString();
        json.expirationHeight = this.expirationHeight;
        json.id = this.id.toString();
        return json;
    }

    @Override
    public String toString() {
        return JsonStream.serialize(this.toJson());
    }
}
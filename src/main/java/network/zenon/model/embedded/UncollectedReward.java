package network.zenon.model.embedded;

import com.jsoniter.output.JsonStream;

import network.zenon.model.JsonConvertible;
import network.zenon.model.embedded.json.JUncollectedReward;
import network.zenon.model.primitives.Address;

public class UncollectedReward implements JsonConvertible<JUncollectedReward> {
    private final Address address;
    private final long znnAmount;
    private final long qsrAmount;

    public UncollectedReward(JUncollectedReward json) {
        this.address = Address.parse(json.address);
        this.znnAmount = json.znnAmount;
        this.qsrAmount = json.qsrAmount;
    }

    public UncollectedReward(Address address, long znnAmount, long qsrAmount) {
        this.address = address;
        this.znnAmount = znnAmount;
        this.qsrAmount = qsrAmount;
    }

    public Address getAddress() {
        return this.address;
    }

    public long getZnnAmount() {
        return this.znnAmount;
    }

    public long getQsrAmount() {
        return this.qsrAmount;
    }

    @Override
    public JUncollectedReward toJson() {
        JUncollectedReward json = new JUncollectedReward();
        json.address = this.address.toString();
        json.znnAmount = this.znnAmount;
        json.qsrAmount = this.qsrAmount;
        return json;
    }

    @Override
    public String toString() {
        return JsonStream.serialize(this.toJson());
    }
}
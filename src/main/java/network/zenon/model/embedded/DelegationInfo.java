package network.zenon.model.embedded;

import network.zenon.model.JsonConvertible;
import network.zenon.model.embedded.json.JDelegationInfo;
import network.zenon.utils.AmountUtils;
import network.zenon.utils.JsonUtils;

public class DelegationInfo implements JsonConvertible<JDelegationInfo> {
    private final String name;
    private final long status;
    private final long weight;
    private final double weightWithDecimals;

    public DelegationInfo(JDelegationInfo json) {
        this.name = json.name;
        this.status = json.status;
        this.weight = json.weight;
        this.weightWithDecimals = AmountUtils.addDecimals(this.weight, 8);
    }

    public DelegationInfo(String name, long status, long weight) {
        this.name = name;
        this.status = status;
        this.weight = weight;
        this.weightWithDecimals = AmountUtils.addDecimals(this.weight, 8);
    }

    public String getName() {
        return this.name;
    }

    public long getStatus() {
        return this.status;
    }

    public long getWeight() {
        return this.weight;
    }

    public double getWeightWithDecimals() {
        return this.weightWithDecimals;
    }

    public boolean isPillarActive() {
        return this.status == 1;
    }

    @Override
    public JDelegationInfo toJson() {
        JDelegationInfo json = new JDelegationInfo();
        json.name = this.name;
        json.status = this.status;
        json.weight = this.weight;
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}
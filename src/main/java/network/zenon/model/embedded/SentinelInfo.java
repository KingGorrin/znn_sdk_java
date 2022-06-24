package network.zenon.model.embedded;

import network.zenon.model.JsonConvertible;
import network.zenon.model.embedded.json.JSentinelInfo;
import network.zenon.model.primitives.Address;
import network.zenon.utils.JsonUtils;

public class SentinelInfo implements JsonConvertible<JSentinelInfo> {
    private final Address owner;
    private final long registrationTimestamp;
    private final boolean isRevocable;
    private final long revokeCooldown;
    private final boolean active;

    public SentinelInfo(JSentinelInfo json) {
        this.owner = Address.parse(json.owner);
        this.registrationTimestamp = json.registrationTimestamp;
        this.isRevocable = json.isRevocable;
        this.revokeCooldown = json.revokeCooldown;
        this.active = json.active;
    }

    public SentinelInfo(Address address, long registrationTimestamp, boolean isRevocable, long revokeCooldown,
            boolean active) {
        this.owner = address;
        this.registrationTimestamp = registrationTimestamp;
        this.isRevocable = isRevocable;
        this.revokeCooldown = revokeCooldown;
        this.active = active;
    }

    public Address getOwner() {
        return this.owner;
    }

    public long getRegistrationTimestamp() {
        return this.registrationTimestamp;
    }

    public boolean isRevocable() {
        return this.isRevocable;
    }

    public long getRevokeCooldown() {
        return this.revokeCooldown;
    }

    public boolean getActive() {
        return this.active;
    }

    @Override
    public JSentinelInfo toJson() {
        JSentinelInfo json = new JSentinelInfo();
        json.owner = this.owner.toString();
        json.registrationTimestamp = this.registrationTimestamp;
        json.isRevocable = this.isRevocable;
        json.revokeCooldown = this.revokeCooldown;
        json.active = this.active;
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}
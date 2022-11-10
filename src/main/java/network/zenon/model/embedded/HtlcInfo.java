package network.zenon.model.embedded;

import network.zenon.model.JsonConvertible;
import network.zenon.model.embedded.json.JHtlcInfo;
import network.zenon.model.primitives.Address;
import network.zenon.model.primitives.Hash;
import network.zenon.model.primitives.TokenStandard;
import network.zenon.utils.BytesUtils;
import network.zenon.utils.JsonUtils;

public class HtlcInfo implements JsonConvertible<JHtlcInfo> {
    private final Hash id;
    private final Address timeLocked;
    private final Address hashLocked;
    private final TokenStandard tokenStandard;
    private final long amount;
    private final long expirationTime;
    private final int hashType;
    private final int keyMaxSize;
    private final byte[] hashLock;

    public HtlcInfo(JHtlcInfo json) {
        this.id = Hash.parse(json.id);
        this.timeLocked = Address.parse(json.timeLocked);
        this.hashLocked = Address.parse(json.hashLocked);
        this.tokenStandard = TokenStandard.parse(json.tokenStandard);
        this.amount = json.amount;
        this.expirationTime = json.expirationTime;
        this.hashType = json.hashType;
        this.keyMaxSize = json.keyMaxSize;
        this.hashLock = BytesUtils.fromBase64String(json.hashLock);
    }

    public Hash getId() {
        return this.id;
    }

    public Address getTimeLocked() {
        return this.timeLocked;
    }

    public Address getHashLocked() {
        return this.hashLocked;
    }

    public TokenStandard getTokenStandard() {
        return this.tokenStandard;
    }

    public long getAmount() {
        return this.amount;
    }

    public long getExpirationTime() {
        return this.expirationTime;
    }

    public int getHashType() {
        return this.hashType;
    }

    public int getKeyMaxSize() {
        return this.keyMaxSize;
    }

    public byte[] getHashLock() {
        return this.hashLock;
    }

    @Override
    public JHtlcInfo toJson() {
        JHtlcInfo json = new JHtlcInfo();
        json.timeLocked = this.timeLocked.toString();
        json.hashLocked = this.hashLocked.toString();
        json.tokenStandard = this.tokenStandard.toString();
        json.amount = this.amount;
        json.expirationTime = this.expirationTime;
        json.hashType = this.hashType;
        json.keyMaxSize = this.keyMaxSize;
        json.hashLock = BytesUtils.toBase64String(this.hashLock);
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}
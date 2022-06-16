package network.zenon.model.embedded;

import com.jsoniter.output.JsonStream;

import network.zenon.model.JsonConvertible;
import network.zenon.model.embedded.json.JPillarInfo;
import network.zenon.model.primitives.Address;

public class PillarInfo implements JsonConvertible<JPillarInfo> {
    public static final int UNKNOWN_TYPE = 0;
    public static final int LEACY_PILLAR_TYPE = 1;
    public static final int REGULAR_PILLAR_TYPE = 2;

    private final String name;
    private final long rank;
    private final int type;
    private final Address ownerAddress;
    private final Address producerAddress;
    private final Address withdrawAddress;
    private final long giveMomentumRewardPercentage;
    private final long giveDelegateRewardPercentage;
    private final boolean isRevocable;
    private final long revokeCooldown;
    private final long revokeTimestamp;
    private final PillarEpochStats currentStats;
    private final long weight;
    private final long producedMomentums;
    private final long expectedMomentums;

    public PillarInfo(JPillarInfo json) {
        this.name = json.name;
        this.rank = json.rank;
        this.type = json.type; // UnknownType
        this.ownerAddress = Address.parse(json.ownerAddress);
        this.producerAddress = Address.parse(json.producerAddress);
        this.withdrawAddress = Address.parse(json.withdrawAddress);
        this.giveMomentumRewardPercentage = json.giveMomentumRewardPercentage;
        this.giveDelegateRewardPercentage = json.giveDelegateRewardPercentage;
        this.isRevocable = json.isRevocable;
        this.revokeCooldown = json.revokeCooldown;
        this.revokeTimestamp = json.revokeTimestamp;
        this.currentStats = new PillarEpochStats(json.currentStats);
        this.weight = json.weight;
        this.producedMomentums = json.producedMomentums;
        this.expectedMomentums = json.expectedMomentums;
    }

    public PillarInfo(String name, long rank, int type, Address ownerAddress, Address producerAddress,
            Address withdrawAddress, long giveMomentumRewardPercentage, long giveDelegateRewardPercentage,
            boolean isRevocable, long revokeCooldown, long revokeTimestamp, PillarEpochStats currentStats, long weight,
            long producedMomentums, long expectedMomentums) {
        this.name = name;
        this.rank = rank;
        this.type = type; // UnknownType
        this.ownerAddress = ownerAddress;
        this.producerAddress = producerAddress;
        this.withdrawAddress = withdrawAddress;
        this.giveMomentumRewardPercentage = giveMomentumRewardPercentage;
        this.giveDelegateRewardPercentage = giveDelegateRewardPercentage;
        this.isRevocable = isRevocable;
        this.revokeCooldown = revokeCooldown;
        this.revokeTimestamp = revokeTimestamp;
        this.currentStats = currentStats;
        this.weight = weight;
        this.producedMomentums = producedMomentums;
        this.expectedMomentums = expectedMomentums;
    }

    public String getName() {
        return this.name;
    }

    public long getRank() {
        return this.rank;
    }

    public int getType() {
        return this.type;
    }

    public Address getOwnerAddress() {
        return this.ownerAddress;
    }

    public Address getProducerAddress() {
        return this.producerAddress;
    }

    public Address getWithdrawAddress() {
        return this.withdrawAddress;
    }

    public long getGiveMomentumRewardPercentage() {
        return this.giveMomentumRewardPercentage;
    }

    public long getGiveDelegateRewardPercentage() {
        return this.giveDelegateRewardPercentage;
    }

    public boolean getIsRevocable() {
        return this.isRevocable;
    }

    public long getRevokeCooldown() {
        return this.revokeCooldown;
    }

    public long getRevokeTimestamp() {
        return this.revokeTimestamp;
    }

    public PillarEpochStats getCurrentStats() {
        return this.currentStats;
    }

    public long getWeight() {
        return this.weight;
    }

    public long getProducedMomentums() {
        return this.producedMomentums;
    }

    public long getExpectedMomentums() {
        return this.expectedMomentums;
    }

    @Override
    public JPillarInfo toJson() {
        JPillarInfo json = new JPillarInfo();
        json.name = this.name;
        json.rank = this.rank;
        json.ownerAddress = this.ownerAddress.toString();
        json.producerAddress = this.producerAddress.toString();
        json.withdrawAddress = this.withdrawAddress.toString();
        json.isRevocable = this.isRevocable;
        json.revokeCooldown = this.revokeCooldown;
        json.currentStats = this.currentStats.toJson();
        json.weight = this.weight;
        return json;
    }

    @Override
    public String toString() {
        return JsonStream.serialize(this.toJson());
    }
}
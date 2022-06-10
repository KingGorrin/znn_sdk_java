package network.zenon.model.embedded.json;

public class JPillarInfo
{
    public String name;
    public long rank;
    public int type;
    public String ownerAddress;
    public String producerAddress;
    public String withdrawAddress;
    public long giveMomentumRewardPercentage;
    public long giveDelegateRewardPercentage;
    public boolean isRevocable;
    public long revokeCooldown;
    public long revokeTimestamp;
    public JPillarEpochStats currentStats;
    public long weight;
    public long producedMomentums;
    public long expectedMomentums;
}
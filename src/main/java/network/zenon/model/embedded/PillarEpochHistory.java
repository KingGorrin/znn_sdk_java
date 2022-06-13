package network.zenon.model.embedded;

import com.jsoniter.output.JsonStream;

import network.zenon.model.IJsonConvertible;
import network.zenon.model.embedded.json.JPillarEpochHistory;

public class PillarEpochHistory implements IJsonConvertible<JPillarEpochHistory> {
    private final String name;
    private final long epoch;
    private final long giveBlockRewardPercentage;
    private final long giveDelegateRewardPercentage;
    private final long producedBlockNum;
    private final long expectedBlockNum;
    private final long weight;

    public PillarEpochHistory(JPillarEpochHistory json) {
        this.name = json.name;
        this.epoch = json.epoch;
        this.giveBlockRewardPercentage = json.giveBlockRewardPercentage;
        this.giveDelegateRewardPercentage = json.giveDelegateRewardPercentage;
        this.producedBlockNum = json.producedBlockNum;
        this.expectedBlockNum = json.expectedBlockNum;
        this.weight = json.weight;
    }

    public PillarEpochHistory(String name, long epoch, long giveBlockRewardPercentage,
            long giveDelegateRewardPercentage, long producedBlockNum, long expectedBlockNum, long weight) {
        this.name = name;
        this.epoch = epoch;
        this.giveBlockRewardPercentage = giveBlockRewardPercentage;
        this.giveDelegateRewardPercentage = giveDelegateRewardPercentage;
        this.producedBlockNum = producedBlockNum;
        this.expectedBlockNum = expectedBlockNum;
        this.weight = weight;
    }

    public String getName() {
        return this.name;
    }

    public long getEpoch() {
        return this.epoch;
    }

    public long getGiveBlockRewardPercentage() {
        return this.giveBlockRewardPercentage;
    }

    public long getGiveDelegateRewardPercentage() {
        return this.giveDelegateRewardPercentage;
    }

    public long getProducedBlockNum() {
        return this.producedBlockNum;
    }

    public long getExpectedBlockNum() {
        return this.expectedBlockNum;
    }

    public long getWeight() {
        return this.weight;
    }

    @Override
    public JPillarEpochHistory toJson() {
        JPillarEpochHistory json = new JPillarEpochHistory();
        json.name = this.name;
        json.epoch = this.epoch;
        json.giveBlockRewardPercentage = this.giveBlockRewardPercentage;
        json.giveDelegateRewardPercentage = this.giveDelegateRewardPercentage;
        json.producedBlockNum = this.producedBlockNum;
        json.expectedBlockNum = this.expectedBlockNum;
        json.weight = this.weight;
        return json;
    }

    @Override
    public String toString() {
        return JsonStream.serialize(this.toJson());
    }
}
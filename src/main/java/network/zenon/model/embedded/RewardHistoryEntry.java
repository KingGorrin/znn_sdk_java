package network.zenon.model.embedded;

import com.jsoniter.output.JsonStream;

import network.zenon.model.IJsonConvertible;
import network.zenon.model.embedded.json.JRewardHistoryEntry;

public class RewardHistoryEntry implements IJsonConvertible<JRewardHistoryEntry> {
    private final long epoch;
    private final long znnAmount;
    private final long qsrAmount;

    public RewardHistoryEntry(JRewardHistoryEntry json) {
        this.epoch = json.epoch;
        this.znnAmount = json.znnAmount;
        this.qsrAmount = json.qsrAmount;
    }

    public RewardHistoryEntry(long epoch, long znnAmount, long qsrAmount) {
        this.epoch = epoch;
        this.znnAmount = znnAmount;
        this.qsrAmount = qsrAmount;
    }

    public long getEpoch() {
        return this.epoch;
    }

    public long getZnnAmount() {
        return this.znnAmount;
    }

    public long getQsrAmount() {
        return this.qsrAmount;
    }

    @Override
    public JRewardHistoryEntry toJson() {
        JRewardHistoryEntry json = new JRewardHistoryEntry();
        json.epoch = this.epoch;
        json.znnAmount = this.znnAmount;
        json.qsrAmount = this.qsrAmount;
        return json;
    }

    @Override
    public String toString() {
        return JsonStream.serialize(this.toJson());
    }
}
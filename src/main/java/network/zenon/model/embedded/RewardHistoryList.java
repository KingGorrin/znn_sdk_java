package network.zenon.model.embedded;

import java.util.Collections;
import java.util.List;

import com.jsoniter.output.JsonStream;

import network.zenon.model.IJsonConvertible;
import network.zenon.model.embedded.json.JRewardHistoryList;

public class RewardHistoryList implements IJsonConvertible<JRewardHistoryList> {
    private final long count;
    private final List<RewardHistoryEntry> list;

    public RewardHistoryList(JRewardHistoryList json) {
        this.count = json.count;
        this.list = json.list != null
                ? Collections.unmodifiableList(json.list.stream().map(x -> new RewardHistoryEntry(x)).toList())
                : Collections.emptyList();
    }

    public RewardHistoryList(long count, List<RewardHistoryEntry> list) {
        this.count = count;
        this.list = Collections.unmodifiableList(list);
    }

    public long getCount() {
        return this.count;
    }

    public List<RewardHistoryEntry> getList() {
        return this.list;
    }

    @Override
    public JRewardHistoryList toJson() {
        JRewardHistoryList json = new JRewardHistoryList();
        json.count = this.count;
        json.list = this.list.stream().map(RewardHistoryEntry::toJson).toList();
        return json;
    }

    @Override
    public String toString() {
        return JsonStream.serialize(this.toJson());
    }
}
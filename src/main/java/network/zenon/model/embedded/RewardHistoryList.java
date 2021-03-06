package network.zenon.model.embedded;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import network.zenon.model.JsonConvertible;
import network.zenon.model.embedded.json.JRewardHistoryList;
import network.zenon.utils.JsonUtils;

public class RewardHistoryList implements JsonConvertible<JRewardHistoryList> {
    private final long count;
    private final List<RewardHistoryEntry> list;

    public RewardHistoryList(JRewardHistoryList json) {
        this.count = json.count;
        this.list = json.list != null
                ? Collections.unmodifiableList(
                        json.list.stream().map(x -> new RewardHistoryEntry(x)).collect(Collectors.toList()))
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
        json.list = this.list.stream().map(RewardHistoryEntry::toJson).collect(Collectors.toList());
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}
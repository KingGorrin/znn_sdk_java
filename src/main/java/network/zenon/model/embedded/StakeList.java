package network.zenon.model.embedded;

import java.util.Collections;
import java.util.List;

import network.zenon.model.JsonConvertible;
import network.zenon.model.embedded.json.JStakeList;
import network.zenon.utils.JsonUtils;

public class StakeList implements JsonConvertible<JStakeList> {
    private final long totalAmount;
    private final long totalWeightedAmount;
    private final long count;
    private final List<StakeEntry> list;

    public StakeList(JStakeList json) {
        this.totalAmount = json.totalAmount;
        this.totalWeightedAmount = json.totalWeightedAmount;
        this.count = json.count;
        this.list = json.list != null
                ? Collections.unmodifiableList(json.list.stream().map(x -> new StakeEntry(x)).toList())
                : Collections.emptyList();
    }

    public StakeList(long totalAmount, long totalWeightedAmount, long count, List<StakeEntry> list) {
        this.totalAmount = totalAmount;
        this.totalWeightedAmount = totalWeightedAmount;
        this.count = count;
        this.list = Collections.unmodifiableList(list);
    }

    public long getTotalAmount() {
        return this.totalAmount;
    }

    public long getTotalWeightedAmount() {
        return this.totalWeightedAmount;
    }

    public long getCount() {
        return this.count;
    }

    public List<StakeEntry> getList() {
        return this.list;
    }

    @Override
    public JStakeList toJson() {
        JStakeList json = new JStakeList();
        json.totalAmount = this.totalAmount;
        json.totalWeightedAmount = this.totalWeightedAmount;
        json.count = this.count;
        json.list = this.list.stream().map(StakeEntry::toJson).toList();
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}
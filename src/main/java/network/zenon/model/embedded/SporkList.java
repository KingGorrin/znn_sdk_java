package network.zenon.model.embedded;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import network.zenon.model.JsonConvertible;
import network.zenon.model.embedded.json.JSporkList;
import network.zenon.utils.JsonUtils;

public class SporkList implements JsonConvertible<JSporkList> {
    private final long count;
    private final List<Spork> list;

    public SporkList(JSporkList json) {
        this.count = json.count;
        this.list = json.list != null
                ? Collections
                        .unmodifiableList(json.list.stream().map(x -> new Spork(x)).collect(Collectors.toList()))
                : Collections.emptyList();
    }

    public SporkList(long count, List<Spork> list) {
        this.count = count;
        this.list = Collections.unmodifiableList(list);
    }

    public long getCount() {
        return this.count;
    }

    public List<Spork> getList() {
        return this.list;
    }

    @Override
    public JSporkList toJson() {
        JSporkList json = new JSporkList();
        json.count = this.count;
        json.list = this.list.stream().map(Spork::toJson).collect(Collectors.toList());
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}
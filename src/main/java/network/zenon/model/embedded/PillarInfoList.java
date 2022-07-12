package network.zenon.model.embedded;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import network.zenon.model.JsonConvertible;
import network.zenon.model.embedded.json.JPillarInfoList;
import network.zenon.utils.JsonUtils;

public class PillarInfoList implements JsonConvertible<JPillarInfoList> {
    private final long count;
    private final List<PillarInfo> list;

    public PillarInfoList(JPillarInfoList json) {
        this.count = json.count;
        this.list = json.list != null
                ? Collections
                        .unmodifiableList(json.list.stream().map(x -> new PillarInfo(x)).collect(Collectors.toList()))
                : Collections.emptyList();
    }

    public PillarInfoList(long count, List<PillarInfo> list) {
        this.count = count;
        this.list = Collections.unmodifiableList(list);
    }

    public long getCount() {
        return this.count;
    }

    public List<PillarInfo> getList() {
        return this.list;
    }

    @Override
    public JPillarInfoList toJson() {
        JPillarInfoList json = new JPillarInfoList();
        json.count = this.count;
        json.list = this.list.stream().map(PillarInfo::toJson).collect(Collectors.toList());
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}
package network.zenon.model.embedded;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import network.zenon.model.JsonConvertible;
import network.zenon.model.embedded.json.JSentinelInfoList;
import network.zenon.utils.JsonUtils;

public class SentinelInfoList implements JsonConvertible<JSentinelInfoList> {
    private final long count;
    private final List<SentinelInfo> list;

    public SentinelInfoList(JSentinelInfoList json) {
        this.count = json.count;
        this.list = json.list != null
                ? Collections
                        .unmodifiableList(json.list.stream().map(x -> new SentinelInfo(x)).collect(Collectors.toList()))
                : Collections.emptyList();
    }

    public SentinelInfoList(long count, List<SentinelInfo> list) {
        this.count = count;
        this.list = Collections.unmodifiableList(list);
    }

    public long getCount() {
        return this.count;
    }

    public List<SentinelInfo> getList() {
        return this.list;
    }

    @Override
    public JSentinelInfoList toJson() {
        JSentinelInfoList json = new JSentinelInfoList();
        json.count = this.count;
        json.list = this.list.stream().map(SentinelInfo::toJson).collect(Collectors.toList());
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}
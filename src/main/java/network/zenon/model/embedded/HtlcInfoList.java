package network.zenon.model.embedded;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import network.zenon.model.JsonConvertible;
import network.zenon.model.embedded.json.JHtlcInfoList;
import network.zenon.utils.JsonUtils;

public class HtlcInfoList implements JsonConvertible<JHtlcInfoList> {
    private final long count;
    private final List<HtlcInfo> list;

    public HtlcInfoList(JHtlcInfoList json) {
        this.count = json.count;
        this.list = json.list != null
                ? Collections
                        .unmodifiableList(json.list.stream().map(x -> new HtlcInfo(x)).collect(Collectors.toList()))
                : Collections.emptyList();
    }

    public HtlcInfoList(long count, List<HtlcInfo> list) {
        this.count = count;
        this.list = Collections.unmodifiableList(list);
    }

    public long getCount() {
        return this.count;
    }

    public List<HtlcInfo> getList() {
        return this.list;
    }

    @Override
    public JHtlcInfoList toJson() {
        JHtlcInfoList json = new JHtlcInfoList();
        json.count = this.count;
        json.list = this.list.stream().map(HtlcInfo::toJson).collect(Collectors.toList());
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}
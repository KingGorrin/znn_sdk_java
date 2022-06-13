package network.zenon.model.embedded;

import java.util.Collections;
import java.util.List;

import com.jsoniter.output.JsonStream;

import network.zenon.model.IJsonConvertible;
import network.zenon.model.embedded.json.JFusionEntryList;

public class FusionEntryList implements IJsonConvertible<JFusionEntryList> {
    private final long qsrAmount;
    private final long count;
    private final List<FusionEntry> list;

    public FusionEntryList(JFusionEntryList json) {
        this.qsrAmount = json.qsrAmount;
        this.count = json.count;
        this.list = json.list != null
                ? Collections.unmodifiableList(json.list.stream().map(x -> new FusionEntry(x)).toList())
                : Collections.emptyList();
    }

    public FusionEntryList(long qsrAmount, long count, List<FusionEntry> list) {
        this.qsrAmount = qsrAmount;
        this.count = count;
        this.list = Collections.unmodifiableList(list);
    }

    public long getQsrAmount() {
        return this.qsrAmount;
    }

    public long getCount() {
        return this.count;
    }

    public List<FusionEntry> getList() {
        return this.list;
    }

    @Override
    public JFusionEntryList toJson() {
        JFusionEntryList json = new JFusionEntryList();
        json.qsrAmount = this.qsrAmount;
        json.count = this.count;
        json.list = this.list.stream().map(FusionEntry::toJson).toList();
        return json;
    }

    @Override
    public String toString() {
        return JsonStream.serialize(this.toJson());
    }
}
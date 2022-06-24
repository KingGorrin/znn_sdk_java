package network.zenon.model.nom;

import java.util.Collections;
import java.util.List;

import network.zenon.model.JsonConvertible;
import network.zenon.model.nom.json.JDetailedMomentumList;
import network.zenon.utils.JsonUtils;

public class DetailedMomentumList implements JsonConvertible<JDetailedMomentumList> {
    private final long count;
    private final List<DetailedMomentum> list;

    public DetailedMomentumList(JDetailedMomentumList json) {
        this.count = json.count;
        this.list = json.list != null
                ? Collections.unmodifiableList(json.list.stream().map(x -> new DetailedMomentum(x)).toList())
                : Collections.emptyList();
    }

    public DetailedMomentumList(long count, List<DetailedMomentum> list) {
        this.count = count;
        this.list = Collections.unmodifiableList(list);
    }

    public long getCount() {
        return this.count;
    }

    public List<DetailedMomentum> getList() {
        return this.list;
    }

    @Override
    public JDetailedMomentumList toJson() {
        JDetailedMomentumList json = new JDetailedMomentumList();
        json.count = this.count;
        json.list = this.list.stream().map(DetailedMomentum::toJson).toList();
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}
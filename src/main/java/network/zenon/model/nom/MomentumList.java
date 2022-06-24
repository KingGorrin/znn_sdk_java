package network.zenon.model.nom;

import java.util.Collections;
import java.util.List;

import network.zenon.model.JsonConvertible;
import network.zenon.model.nom.json.JMomentumList;
import network.zenon.utils.JsonUtils;

public class MomentumList implements JsonConvertible<JMomentumList> {
    private final long count;
    private final List<Momentum> list;

    public MomentumList(JMomentumList json) {
        this.count = json.count;
        this.list = json.list != null
                ? Collections.unmodifiableList(json.list.stream().map(x -> new Momentum(x)).toList())
                : Collections.emptyList();
    }

    public MomentumList(long count, List<Momentum> list) {
        this.count = count;
        this.list = Collections.unmodifiableList(list);
    }

    public long getCount() {
        return this.count;
    }

    public List<Momentum> getList() {
        return this.list;
    }

    @Override
    public JMomentumList toJson() {
        JMomentumList json = new JMomentumList();
        json.count = this.count;
        json.list = this.list.stream().map(Momentum::toJson).toList();
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}
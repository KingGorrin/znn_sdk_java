package network.zenon.model.embedded;

import network.zenon.model.JsonConvertible;
import network.zenon.model.embedded.json.JVoteBreakdown;
import network.zenon.model.primitives.Hash;
import network.zenon.utils.JsonUtils;

public class VoteBreakdown implements JsonConvertible<JVoteBreakdown> {
    private final Hash id;
    private final long yes;
    private final long no;
    private final long total;

    public VoteBreakdown(JVoteBreakdown json) {
        this.id = Hash.parse(json.id);
        this.yes = json.yes;
        this.no = json.no;
        this.total = json.total;
    }

    public VoteBreakdown(Hash id, long yes, long no, long total) {
        this.id = id;
        this.yes = yes;
        this.no = no;
        this.total = total;
    }

    public Hash getId() {
        return this.id;
    }

    public long getYes() {
        return this.yes;
    }

    public long getNo() {
        return this.no;
    }

    public long getTotal() {
        return this.total;
    }

    @Override
    public JVoteBreakdown toJson() {
        JVoteBreakdown json = new JVoteBreakdown();
        json.id = this.id.toString();
        json.yes = this.yes;
        json.no = this.no;
        json.total = this.total;
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}
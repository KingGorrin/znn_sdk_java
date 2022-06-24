package network.zenon.model.embedded;

import network.zenon.model.JsonConvertible;
import network.zenon.model.embedded.json.JPillarVote;
import network.zenon.model.primitives.Hash;
import network.zenon.utils.JsonUtils;

public class PillarVote implements JsonConvertible<JPillarVote> {
    private final Hash id;
    private final String name;
    private final long vote;

    public PillarVote(JPillarVote json) {
        this.id = Hash.parse(json.id);
        this.name = json.name;
        this.vote = json.vote;
    }

    public PillarVote(Hash id, String name, long vote) {
        this.id = id;
        this.name = name;
        this.vote = vote;
    }

    public Hash getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public long getVote() {
        return this.vote;
    }

    @Override
    public JPillarVote toJson() {
        JPillarVote json = new JPillarVote();
        json.id = this.id.toString();
        json.name = this.name;
        json.vote = this.vote;
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}
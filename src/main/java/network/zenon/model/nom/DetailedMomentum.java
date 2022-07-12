package network.zenon.model.nom;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import network.zenon.model.JsonConvertible;
import network.zenon.model.nom.json.JDetailedMomentum;
import network.zenon.utils.JsonUtils;

public class DetailedMomentum implements JsonConvertible<JDetailedMomentum> {
    private final List<AccountBlock> blocks;
    private final Momentum momentum;

    public DetailedMomentum(JDetailedMomentum json) {
        this.blocks = json.blocks != null
                ? Collections.unmodifiableList(
                        json.blocks.stream().map(x -> new AccountBlock(x)).collect(Collectors.toList()))
                : Collections.emptyList();
        this.momentum = new Momentum(json.momentum);
    }

    public List<AccountBlock> getBlocks() {
        return this.blocks;
    }

    public Momentum getMomentum() {
        return this.momentum;
    }

    @Override
    public JDetailedMomentum toJson() {
        JDetailedMomentum json = new JDetailedMomentum();
        json.blocks = this.blocks.stream().map(x -> x.toJson()).collect(Collectors.toList());
        json.momentum = this.momentum.toJson();
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}
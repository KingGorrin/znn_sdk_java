package network.zenon.model.nom;

import java.util.Collections;
import java.util.List;

import com.jsoniter.output.JsonStream;

import network.zenon.model.IJsonConvertible;
import network.zenon.model.nom.json.JDetailedMomentum;

public class DetailedMomentum implements IJsonConvertible<JDetailedMomentum> {
	private final List<AccountBlock> blocks;
	private final Momentum momentum;
    
	public DetailedMomentum(JDetailedMomentum json) {
		this.blocks = json.blocks != null
                ? Collections.unmodifiableList(json.blocks.stream().map(x -> new AccountBlock(x)).toList())
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
    	json.blocks = this.blocks.stream().map(x -> x.toJson()).toList();
    	json.momentum = this.momentum.toJson();
        return json;
    }
    
    @Override
    public String toString() {
    	return JsonStream.serialize(this.toJson());
    }
}
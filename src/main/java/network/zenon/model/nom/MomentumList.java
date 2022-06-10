package network.zenon.model.nom;

import java.util.Collections;
import java.util.List;

import com.jsoniter.output.JsonStream;

import network.zenon.model.IJsonConvertible;
import network.zenon.model.nom.json.JMomentumList;

public class MomentumList implements IJsonConvertible<JMomentumList> {
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

    public long getCount () {
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
    	return JsonStream.serialize(this.toJson());
    }
}
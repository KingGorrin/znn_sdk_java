package network.zenon.model.embedded;

import java.util.Collections;
import java.util.List;

import com.jsoniter.output.JsonStream;

import network.zenon.model.IJsonConvertible;
import network.zenon.model.embedded.json.JPillarInfoList;

public class PillarInfoList implements IJsonConvertible<JPillarInfoList> {
	private final long count;
	private final List<PillarInfo> list;
    
    public PillarInfoList(JPillarInfoList json) {
        this.count = json.count;
        this.list = json.list != null
                ? Collections.unmodifiableList(json.list.stream().map(x -> new PillarInfo(x)).toList())
                : Collections.emptyList();
    }
    
    public PillarInfoList(long count, List<PillarInfo> list) {
    	this.count = count;
    	this.list = Collections.unmodifiableList(list);
    }

    public long getCount () {
    	return this.count;
    }
    
    public List<PillarInfo> getList() {
    	return this.list;
    }
    
    @Override
    public JPillarInfoList toJson()  {
    	JPillarInfoList json = new JPillarInfoList();
    	json.count = this.count;
    	json.list =  this.list.stream().map(PillarInfo::toJson).toList();
        return json;
    }
    
    @Override
    public String toString() {
    	return JsonStream.serialize(this.toJson());
    }
}
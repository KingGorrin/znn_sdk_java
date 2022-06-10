package network.zenon.model.embedded;

import java.util.Collections;
import java.util.List;

import com.jsoniter.output.JsonStream;

import network.zenon.model.IJsonConvertible;
import network.zenon.model.embedded.json.JPillarEpochHistoryList;

public class PillarEpochHistoryList implements IJsonConvertible<JPillarEpochHistoryList> {
	private final long count;
	private final List<PillarEpochHistory> list;
	
    public PillarEpochHistoryList(JPillarEpochHistoryList json) {
    	this.count = json.count;
        this.list = json.list != null
                ? Collections.unmodifiableList(json.list.stream().map(x -> new PillarEpochHistory(x)).toList())
                : Collections.emptyList();
    }

    public PillarEpochHistoryList(long count, List<PillarEpochHistory> list) {
    	this.count = count;
    	this.list = Collections.unmodifiableList(list);
    }

    public long getCount () {
    	return this.count;
    }
    
    public List<PillarEpochHistory> getList() {
    	return this.list;
    }
    
    @Override
    public JPillarEpochHistoryList toJson() {
    	JPillarEpochHistoryList json = new JPillarEpochHistoryList();
        json.count = this.count;
        json.list = this.list.stream().map(PillarEpochHistory::toJson).toList();
        return json;
    }
    
    @Override
    public String toString() {
    	return JsonStream.serialize(this.toJson());
    }
}
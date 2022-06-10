package network.zenon.model.nom;

import java.util.Collections;
import java.util.List;

import com.jsoniter.output.JsonStream;

import network.zenon.model.IJsonConvertible;
import network.zenon.model.nom.json.JAccountBlockList;

public class AccountBlockList implements IJsonConvertible<JAccountBlockList> {
	private final long count;
	private final List<AccountBlock> list;
	private boolean more;
	
    public AccountBlockList(JAccountBlockList json) {
    	this.count = json.count;
        this.list = json.list != null
                ? Collections.unmodifiableList(json.list.stream().map(x -> new AccountBlock(x)).toList())
                : Collections.emptyList();
        this.more = json.more;
    }

    public AccountBlockList(long count, List<AccountBlock> list, boolean more) {
    	this.count = count;
    	this.list = Collections.unmodifiableList(list);
    	this.more = more;
    }

    public long getCount () {
    	return this.count;
    }
    
    public List<AccountBlock> getList() {
    	return this.list;
    }
    
    /// If true, there are more than `count` elements, but only these can be retrieved
    public boolean getMore() {
    	return this.more;
    }
    
    @Override
    public JAccountBlockList toJson() {
    	JAccountBlockList json = new JAccountBlockList();
        json.count = this.count;
        json.list = this.list.stream().map(AccountBlock::toJson).toList();
        json.more = this.more;
        return json;
    }
    
    @Override
    public String toString() {
    	return JsonStream.serialize(this.toJson());
    }
}
package network.zenon.model.nom;

import com.jsoniter.output.JsonStream;

import network.zenon.model.IJsonConvertible;
import network.zenon.model.nom.json.JAccountBlockConfirmationDetail;
import network.zenon.model.primitives.Hash;

public class AccountBlockConfirmationDetail implements IJsonConvertible<JAccountBlockConfirmationDetail> {
	private final long numConfirmations;
	private final long momentumHeight;
	private final Hash momentumHash;
	private final long momentumTimestamp;
    
	public AccountBlockConfirmationDetail(JAccountBlockConfirmationDetail json) {
        this.numConfirmations = json.numConfirmations;
        this.momentumHeight = json.momentumHeight;
        this.momentumHash = Hash.parse(json.momentumHash);
        this.momentumTimestamp = json.momentumTimestamp;
    }

    public long getNumConfirmations() {
    	return this.numConfirmations;
    }
    
    public long getMomentumHeight() {
    	return this.momentumHeight;
    }
    
    public Hash getMomentumHash() {
    	return this.momentumHash;
    }
    
    public long getMomentumTimestamp() {
    	return this.momentumTimestamp;
    }

    @Override
    public JAccountBlockConfirmationDetail toJson() {
    	JAccountBlockConfirmationDetail json = new JAccountBlockConfirmationDetail();
    	json.numConfirmations = this.numConfirmations;
    	json.momentumHeight = this.momentumHeight;
    	json.momentumHash = this.momentumHash.toString();
    	json.momentumTimestamp = this.momentumTimestamp;
        return json;
    }
    
    @Override
    public String toString() {
    	return JsonStream.serialize(this.toJson());
    }
}
package network.zenon.model.embedded;

import com.jsoniter.output.JsonStream;

import network.zenon.model.IJsonConvertible;
import network.zenon.model.embedded.json.JStakeEntry;
import network.zenon.model.primitives.Address;
import network.zenon.model.primitives.Hash;

public class StakeEntry implements IJsonConvertible<JStakeEntry> {
	private final long amount;
	private final long weightedAmount;
	private final long startTimestamp;
	private final long expirationTimestamp;
	private final Address address;
    private final Hash id;
    	
    public StakeEntry(JStakeEntry json) {
        this.amount = json.amount;
        this.weightedAmount = json.weightedAmount;
        this.startTimestamp = json.startTimestamp;
        this.expirationTimestamp = json.expirationTimestamp;
        this.address = Address.parse(json.address);
        this.id = Hash.parse(json.id);
    }

    public StakeEntry(
    		long amount, 
    		long weightedAmount, 
    		long startTimestamp, 
    		long expirationTimestamp, 
    		Address address, 
    		Hash id) {
        this.amount = amount;
        this.weightedAmount = weightedAmount;
        this.startTimestamp = startTimestamp;
        this.expirationTimestamp = expirationTimestamp;
        this.address = address;
        this.id = id;
    }

    public long getAmount() {
    	return this.amount;
    }
    
    public long getWeightedAmount() {
    	return this.weightedAmount;
    }
    
    public long getStartTimestamp() {
    	return this.startTimestamp;
    }
    
    public long getExpirationTimestamp() {
    	return this.expirationTimestamp;
    }
    
    public Address getAddress() {
    	return this.address;
    }
    
    public Hash getId() {
    	return this.id;
    }

    @Override
    public JStakeEntry toJson() {
    	JStakeEntry json = new JStakeEntry();
        json.amount = this.amount;
        json.weightedAmount = this.weightedAmount;
        json.startTimestamp = this.startTimestamp;
        json.expirationTimestamp = this.expirationTimestamp;
        json.address = this.address.toString();
        json.id = this.id.toString();
        return json;
    }
    
    @Override
    public String toString() {
    	return JsonStream.serialize(this.toJson());
    }
}
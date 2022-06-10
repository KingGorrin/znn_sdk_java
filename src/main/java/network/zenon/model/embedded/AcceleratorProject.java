package network.zenon.model.embedded;

import network.zenon.Constants;
import network.zenon.model.embedded.json.JAcceleratorProject;
import network.zenon.model.primitives.Hash;
import network.zenon.utils.AmountUtils;

public abstract class AcceleratorProject {
	private final Hash id;
	private final String name;
	private final String description;
	private final String url;
	private final long znnFundsNeeded;
	private final long qsrFundsNeeded;
	private final long creationTimestamp;
	private final AcceleratorProjectStatus status;
	private final VoteBreakdown voteBreakdown;
    
    public AcceleratorProject(JAcceleratorProject json) {
        this.id = Hash.parse(json.id);
        this.name = json.name;
        this.description = json.description;
        this.url = json.url;
        this.znnFundsNeeded = json.znnFundsNeeded;
        this.qsrFundsNeeded = json.qsrFundsNeeded;
        this.creationTimestamp = json.creationTimestamp;
        this.status = AcceleratorProjectStatus.values()[json.status];
        this.voteBreakdown = new VoteBreakdown(json.votes);
    }

    public AcceleratorProject(
        Hash id,
        String name,
        String description,
        String url,
        long znnFundsNeeded,
        long qsrFundsNeeded,
        long creationTimestamp,
        AcceleratorProjectStatus status,
        VoteBreakdown voteBreakdown) {
    	this.id = id;
    	this.name = name;
    	this.description = description;
    	this.url = url;
    	this.znnFundsNeeded = znnFundsNeeded;
    	this.qsrFundsNeeded = qsrFundsNeeded;
    	this.creationTimestamp = creationTimestamp;
    	this.status = status;
    	this.voteBreakdown = voteBreakdown;
    }

    public Hash getId() { 
    	return this.id; 
    }
    
    public String getName() { 
    	return this.name; 
    }
    
    public String getDescription() { 
    	return this.description; 
    }
    
    public String getUrl() { 
    	return this.url; 
    	}
    public long getZnnFundsNeeded() { 
    	return this.znnFundsNeeded;
    }
    
    public long getQsrFundsNeeded() { 
    	return this.qsrFundsNeeded;
    }
    
    public long getCreationTimestamp() { 
    	return this.creationTimestamp; 
    }
    
    public AcceleratorProjectStatus getStatus() { 
    	return this.status; 
    }
    
    public VoteBreakdown getVoteBreakdown() { 
    	return this.voteBreakdown; 
    }

    public double getZnnFundsNeededWithDecimals() {
        return AmountUtils.addDecimals(this.getZnnFundsNeeded(), Constants.ZNN_DECIMALS);
    }

    public double getQsrFundsNeededWithDecimals() {
    	return AmountUtils.addDecimals(this.getQsrFundsNeeded(), Constants.QSR_DECIMALS);
    }

    protected void toJson(JAcceleratorProject json) {
        json.id = this.id.toString();
        json.name = this.name;
        json.description = this.description;
        json.url = this.url;
        json.znnFundsNeeded = this.znnFundsNeeded;
        json.qsrFundsNeeded = this.qsrFundsNeeded;
        json.creationTimestamp = this.creationTimestamp;
        json.status = this.status.ordinal();
        json.votes = this.voteBreakdown.toJson();
    }
}
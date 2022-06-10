package network.zenon.api;

import network.zenon.Constants;
import network.zenon.client.IClient;
import network.zenon.model.nom.AccountBlock;
import network.zenon.model.nom.AccountBlockList;
import network.zenon.model.nom.AccountBlockTemplate;
import network.zenon.model.nom.AccountInfo;
import network.zenon.model.nom.DetailedMomentumList;
import network.zenon.model.nom.Momentum;
import network.zenon.model.nom.MomentumList;
import network.zenon.model.nom.json.JAccountBlock;
import network.zenon.model.nom.json.JAccountBlockList;
import network.zenon.model.nom.json.JAccountInfo;
import network.zenon.model.nom.json.JDetailedMomentumList;
import network.zenon.model.nom.json.JMomentum;
import network.zenon.model.nom.json.JMomentumList;
import network.zenon.model.primitives.Address;
import network.zenon.model.primitives.Hash;

public class LedgerApi {
	private final IClient client;
	
    public LedgerApi(IClient client) {
        this.client = client;
    }

    public IClient getClient() {
    	return this.client;
    }

    /// This method returns null if the account-block was accepted
    public void publishRawTransaction(AccountBlockTemplate accountBlockTemplate) {
        this.client.sendRequest("ledger.publishRawTransaction", new Object[] { accountBlockTemplate.toJson() });
    }

    public AccountBlockList getUnconfirmedBlocksByAddress(Address address) {
    	return this.getUnconfirmedBlocksByAddress(address, 0, Constants.MEMORY_POOL_PAGE_SIZE);
    }
    
    public AccountBlockList getUnconfirmedBlocksByAddress(Address address, int pageIndex, int pageSize) {
    	JAccountBlockList response = this.client.sendRequest("ledger.getUnconfirmedBlocksByAddress", new Object[] { address.toString(), pageIndex, pageSize }, JAccountBlockList.class);
        return new AccountBlockList(response);
    }

    public AccountBlockList getUnreceivedBlocksByAddress(Address address) {
    	return this.getUnreceivedBlocksByAddress(address, 0, Constants.MEMORY_POOL_PAGE_SIZE);
    }
    
    public AccountBlockList getUnreceivedBlocksByAddress(Address address, int pageIndex, int pageSize) {
    	JAccountBlockList response = this.client.sendRequest("ledger.getUnreceivedBlocksByAddress", new Object[] { address.toString(), pageIndex, pageSize }, JAccountBlockList.class);
        return new AccountBlockList(response);
    }

    // Blocks
    public AccountBlock getFrontierAccountBlock(Address address) {
    	JAccountBlock response = this.client.sendRequest("ledger.getFrontierAccountBlock", new Object[] { address.toString() }, JAccountBlock.class);
        return response != null ? new AccountBlock(response) : null;
    }

    public AccountBlock getAccountBlockByHash(Hash hash) {
    	JAccountBlock response = this.client.sendRequest("ledger.getAccountBlockByHash", new Object[] { hash.toString() }, JAccountBlock.class);
        return response != null ? new AccountBlock(response) : null;
    }

    public AccountBlockList getAccountBlocksByHeight(Address address) {
    	return this.getAccountBlocksByHeight(address, 1, Constants.RPC_MAX_PAGE_SIZE);
    }
    
    public AccountBlockList getAccountBlocksByHeight(Address address, int height, int count) {
    	JAccountBlockList response = this.client.sendRequest("ledger.getAccountBlocksByHeight", new Object[] { address.toString(), height, count }, JAccountBlockList.class);
        return new AccountBlockList(response);
    }

    public AccountBlockList getAccountBlocksByPage(Address address) {
    	return this.getAccountBlocksByPage(address, 0, Constants.RPC_MAX_PAGE_SIZE);
    }
    
    /// pageIndex = 0 returns the most recent account blocks sorted descending by height
    public AccountBlockList getAccountBlocksByPage(Address address, int pageIndex, int pageSize) {
    	JAccountBlockList response = this.client.sendRequest("ledger.getAccountBlocksByPage", new Object[] { address.toString(), pageIndex, pageSize }, JAccountBlockList.class);
        return new AccountBlockList(response);
    }

    // Momentum
    public Momentum getFrontierMomentum() {
    	JMomentum response = this.client.sendRequest("ledger.getFrontierMomentum", null, JMomentum.class);
        return new Momentum(response);
    }

    public Momentum getMomentumBeforeTime(long time) {
    	JMomentum response = this.client.sendRequest("ledger.getMomentumBeforeTime", new Object[] { time }, JMomentum.class);
        return response != null ? new Momentum(response) : null;
    }

    public Momentum getMomentumByHash(Hash hash) {
    	JMomentum response = this.client.sendRequest("ledger.getMomentumByHash", new Object[] { hash.toString() }, JMomentum.class);
        return response != null ? new Momentum(response) : null;
    }

    public MomentumList getMomentumsByHeight(long height, long count) {
        height = height < 1 ? 1 : height;
        count = count > Constants.RPC_MAX_PAGE_SIZE ? Constants.RPC_MAX_PAGE_SIZE : count;
        JMomentumList response = this.client.sendRequest("ledger.getMomentumsByHeight", new Object[] { height, count }, JMomentumList.class);
        return new MomentumList(response);
    }

    public MomentumList getMomentumsByPage() {
    	return this.getMomentumsByPage(0, Constants.RPC_MAX_PAGE_SIZE);
    }
    
    /// pageIndex = 0 returns the most recent momentums sorted descending by height
    public MomentumList getMomentumsByPage(int pageIndex, int pageSize) {
    	JMomentumList response = this.client.sendRequest("ledger.getMomentumsByPage", new Object[] { pageIndex, pageSize }, JMomentumList.class);
        return new MomentumList(response);
    }

    public DetailedMomentumList GetDetailedMomentumsByHeight(long height, long count) {
        height = height < 1 ? 1 : height;
        count = count > Constants.RPC_MAX_PAGE_SIZE ? Constants.RPC_MAX_PAGE_SIZE : count;
        JDetailedMomentumList response = this.client.sendRequest("ledger.getDetailedMomentumsByHeight", new Object[] { height, count }, JDetailedMomentumList.class);
        return new DetailedMomentumList(response);
    }

    public AccountInfo GetAccountInfoByAddress(Address address) {
    	JAccountInfo response = this.client.sendRequest("ledger.getAccountInfoByAddress", new Object[] { address.toString() }, JAccountInfo.class);
        return new AccountInfo(response);
    }
}
package network.zenon.api.embedded;

import network.zenon.Constants;
import network.zenon.client.IClient;
import network.zenon.model.embedded.RewardHistoryList;
import network.zenon.model.embedded.StakeList;
import network.zenon.model.embedded.UncollectedReward;
import network.zenon.model.embedded.json.JRewardHistoryList;
import network.zenon.model.embedded.json.JStakeList;
import network.zenon.model.embedded.json.JUncollectedReward;
import network.zenon.model.nom.AccountBlockTemplate;
import network.zenon.model.primitives.Address;
import network.zenon.model.primitives.Hash;

public class StakeApi {
    private final IClient client;

    public StakeApi(IClient client) {
        this.client = client;
    }

    public IClient getClient() {
        return this.client;
    }

    public StakeList getEntriesByAddress(Address address) {
        return getEntriesByAddress(address, 0, Constants.RPC_MAX_PAGE_SIZE);
    }

    public StakeList getEntriesByAddress(Address address, int pageIndex, int pageSize) {
        JStakeList response = this.client.sendRequest("embedded.stake.getEntriesByAddress",
                new Object[] { address.toString(), pageIndex, pageSize }, JStakeList.class);
        return new StakeList(response);
    }

    public UncollectedReward getUncollectedReward(Address address) {
        JUncollectedReward response = this.client.sendRequest("embedded.stake.getUncollectedReward",
                new Object[] { address.toString() }, JUncollectedReward.class);
        return new UncollectedReward(response);
    }

    public RewardHistoryList getFrontierRewardByPage(Address address) {
        return this.getFrontierRewardByPage(address, 0, Constants.RPC_MAX_PAGE_SIZE);
    }

    public RewardHistoryList getFrontierRewardByPage(Address address, int pageIndex, int pageSize) {
        JRewardHistoryList response = this.client.sendRequest("embedded.stake.getFrontierRewardByPage",
                new Object[] { address.toString(), pageIndex, pageSize }, JRewardHistoryList.class);
        return new RewardHistoryList(response);
    }

    // Contract methods
    public AccountBlockTemplate stake(long durationInSec, long amount) {
        throw new UnsupportedOperationException();
    }

    public AccountBlockTemplate cancel(Hash id) {
        throw new UnsupportedOperationException();
    }

    // Common contract methods
    public AccountBlockTemplate collectReward() {
        throw new UnsupportedOperationException();
    }
}

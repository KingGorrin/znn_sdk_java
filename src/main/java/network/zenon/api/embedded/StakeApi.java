package network.zenon.api.embedded;

import network.zenon.Constants;
import network.zenon.client.Client;
import network.zenon.embedded.Definitions;
import network.zenon.model.embedded.RewardHistoryList;
import network.zenon.model.embedded.StakeList;
import network.zenon.model.embedded.UncollectedReward;
import network.zenon.model.embedded.json.JRewardHistoryList;
import network.zenon.model.embedded.json.JStakeList;
import network.zenon.model.embedded.json.JUncollectedReward;
import network.zenon.model.nom.AccountBlockTemplate;
import network.zenon.model.primitives.Address;
import network.zenon.model.primitives.Hash;
import network.zenon.model.primitives.TokenStandard;

public class StakeApi {
    private final Client client;

    public StakeApi(Client client) {
        this.client = client;
    }

    public Client getClient() {
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
        return AccountBlockTemplate.callContract(Address.STAKE_ADDRESS, TokenStandard.ZNN_ZTS, amount,
                Definitions.STAKE.encodeFunction("Stake", durationInSec));
    }

    public AccountBlockTemplate cancel(Hash id) {
        return AccountBlockTemplate.callContract(Address.STAKE_ADDRESS, TokenStandard.ZNN_ZTS, 0,
                Definitions.STAKE.encodeFunction("Cancel", id.getBytes()));
    }

    // Common contract methods
    public AccountBlockTemplate collectReward() {
        return AccountBlockTemplate.callContract(Address.STAKE_ADDRESS, TokenStandard.ZNN_ZTS, 0,
                Definitions.COMMON.encodeFunction("CollectReward"));
    }
}

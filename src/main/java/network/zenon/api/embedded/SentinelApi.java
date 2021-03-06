package network.zenon.api.embedded;

import network.zenon.Constants;
import network.zenon.client.Client;
import network.zenon.embedded.Definitions;
import network.zenon.model.embedded.RewardHistoryList;
import network.zenon.model.embedded.SentinelInfo;
import network.zenon.model.embedded.SentinelInfoList;
import network.zenon.model.embedded.UncollectedReward;
import network.zenon.model.embedded.json.JRewardHistoryList;
import network.zenon.model.embedded.json.JSentinelInfo;
import network.zenon.model.embedded.json.JSentinelInfoList;
import network.zenon.model.embedded.json.JUncollectedReward;
import network.zenon.model.nom.AccountBlockTemplate;
import network.zenon.model.primitives.Address;
import network.zenon.model.primitives.TokenStandard;

public class SentinelApi {
    private final Client client;

    public SentinelApi(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return this.client;
    }

    public SentinelInfoList getAllActive() {
        return this.getAllActive(0, Constants.RPC_MAX_PAGE_SIZE);
    }

    public SentinelInfoList getAllActive(int pageIndex, int pageSize) {
        JSentinelInfoList response = this.client.sendRequest("embedded.sentinel.getAllActive",
                new Object[] { pageIndex, pageSize }, JSentinelInfoList.class);
        return new SentinelInfoList(response);
    }

    public SentinelInfo getByOwner(Address owner) {
        JSentinelInfo response = this.client.sendRequest("embedded.sentinel.getByOwner",
                new Object[] { owner.toString() }, JSentinelInfo.class);
        return response != null ? new SentinelInfo(response) : null;
    }

    public long getDepositedQsr(Address address) {
        return this.client.sendRequest("embedded.sentinel.getDepositedQsr", new Object[] { address.toString() },
                long.class);
    }

    public UncollectedReward getUncollectedReward(Address address) {
        JUncollectedReward response = this.client.sendRequest("embedded.sentinel.getUncollectedReward",
                new Object[] { address.toString() }, JUncollectedReward.class);
        return new UncollectedReward(response);
    }

    public RewardHistoryList getFrontierRewardByPage(Address address) {
        return this.getFrontierRewardByPage(address, 0, Constants.RPC_MAX_PAGE_SIZE);
    }

    public RewardHistoryList getFrontierRewardByPage(Address address, int pageIndex, int pageSize) {
        JRewardHistoryList response = this.client.sendRequest("embedded.sentinel.getFrontierRewardByPage",
                new Object[] { address.toString(), pageIndex, pageSize }, JRewardHistoryList.class);
        return new RewardHistoryList(response);
    }

    // Contract methods
    public AccountBlockTemplate register() {
        return AccountBlockTemplate.callContract(Address.SENTINEL_ADDRESS, TokenStandard.ZNN_ZTS,
                Constants.SENTINEL_REGISTER_ZNN_AMOUNT, Definitions.SENTINEL.encodeFunction("Register"));
    }

    public AccountBlockTemplate revoke() {
        return AccountBlockTemplate.callContract(Address.SENTINEL_ADDRESS, TokenStandard.ZNN_ZTS, 0,
                Definitions.SENTINEL.encodeFunction("Revoke"));
    }

    // Common contract methods
    public AccountBlockTemplate collectReward() {
        return AccountBlockTemplate.callContract(Address.SENTINEL_ADDRESS, TokenStandard.ZNN_ZTS, 0,
                Definitions.COMMON.encodeFunction("CollectReward"));
    }

    public AccountBlockTemplate depositQsr(long amount) {
        return AccountBlockTemplate.callContract(Address.SENTINEL_ADDRESS, TokenStandard.QSR_ZTS, amount,
                Definitions.COMMON.encodeFunction("DepositQsr"));
    }

    public AccountBlockTemplate withdrawQsr() {
        return AccountBlockTemplate.callContract(Address.SENTINEL_ADDRESS, TokenStandard.ZNN_ZTS, 0,
                Definitions.COMMON.encodeFunction("WithdrawQsr"));
    }
}
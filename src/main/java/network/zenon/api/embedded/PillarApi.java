package network.zenon.api.embedded;

import java.io.IOException;
import java.util.List;

import com.jsoniter.JsonIterator;
import com.jsoniter.spi.TypeLiteral;

import network.zenon.Constants;
import network.zenon.client.Client;
import network.zenon.model.embedded.DelegationInfo;
import network.zenon.model.embedded.PillarEpochHistoryList;
import network.zenon.model.embedded.PillarInfo;
import network.zenon.model.embedded.PillarInfoList;
import network.zenon.model.embedded.RewardHistoryList;
import network.zenon.model.embedded.UncollectedReward;
import network.zenon.model.embedded.json.JDelegationInfo;
import network.zenon.model.embedded.json.JPillarEpochHistoryList;
import network.zenon.model.embedded.json.JPillarInfo;
import network.zenon.model.embedded.json.JPillarInfoList;
import network.zenon.model.embedded.json.JRewardHistoryList;
import network.zenon.model.embedded.json.JUncollectedReward;
import network.zenon.model.nom.AccountBlockTemplate;
import network.zenon.model.primitives.Address;

public class PillarApi {
    private final Client client;

    public PillarApi(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return this.client;
    }

    public long getDepositedQsr(Address address) {
        return this.client.sendRequest("embedded.pillar.getDepositedQsr", new Object[] { address.toString() },
                long.class);
    }

    public UncollectedReward getUncollectedReward(Address address) {
        JUncollectedReward response = this.client.sendRequest("embedded.pillar.getUncollectedReward",
                new Object[] { address.toString() }, JUncollectedReward.class);
        return new UncollectedReward(response);
    }

    public RewardHistoryList getFrontierRewardByPage(Address address) {
        return getFrontierRewardByPage(address, 0, Constants.RPC_MAX_PAGE_SIZE);
    }

    public RewardHistoryList getFrontierRewardByPage(Address address, int pageIndex, int pageSize) {
        JRewardHistoryList response = this.client.sendRequest("embedded.pillar.getFrontierRewardByPage",
                new Object[] { address.toString(), pageIndex, pageSize }, JRewardHistoryList.class);
        return new RewardHistoryList(response);
    }

    public long getQsrRegistrationCost() {
        return this.client.sendRequest("embedded.pillar.getQsrRegistrationCost", null, long.class);
    }

    public PillarInfoList getAll() {
        return this.getAll(0, Constants.RPC_MAX_PAGE_SIZE);
    }

    public PillarInfoList getAll(int pageIndex, int pageSize) {
        JPillarInfoList response = this.client.sendRequest("embedded.pillar.getAll",
                new Object[] { pageIndex, pageSize }, JPillarInfoList.class);
        return new PillarInfoList(response);
    }

    public List<PillarInfo> getByOwner(Address address) {
        try {
            Object response = this.client.sendRequest("embedded.pillar.getByOwner",
                    new Object[] { address.toString() });
            JsonIterator iterator = JsonIterator.parse(response.toString());
            List<JPillarInfo> result = iterator.read(new TypeLiteral<List<JPillarInfo>>() {
            });
            return result.stream().map(x -> new PillarInfo(x)).toList();
        } catch (IOException e) {
            return null;
        }
    }

    public PillarInfo getByName(String name) {
        JPillarInfo response = this.client.sendRequest("embedded.pillar.getByName", new Object[] { name },
                JPillarInfo.class);
        return response != null ? new PillarInfo(response) : null;
    }

    public boolean checkNameAvailability(String name) {
        return this.client.sendRequest("embedded.pillar.checkNameAvailability", new Object[] { name }, boolean.class);
    }

    public DelegationInfo getDelegatedPillar(Address address) {
        JDelegationInfo response = this.client.sendRequest("embedded.pillar.getDelegatedPillar",
                new Object[] { address.toString() }, JDelegationInfo.class);
        return response != null ? new DelegationInfo(response) : null;
    }

    public PillarEpochHistoryList getPillarEpochHistory(String name) {
        return this.getPillarEpochHistory(name, 0, Constants.RPC_MAX_PAGE_SIZE);
    }

    public PillarEpochHistoryList getPillarEpochHistory(String name, int pageIndex, int pageSize) {
        JPillarEpochHistoryList response = this.client.sendRequest("embedded.pillar.getPillarEpochHistory",
                new Object[] { name, pageIndex, pageSize }, JPillarEpochHistoryList.class);
        return new PillarEpochHistoryList(response);
    }

    public PillarEpochHistoryList getPillarsHistoryByEpoch(String name) {
        return this.getPillarEpochHistory(name, 0, Constants.RPC_MAX_PAGE_SIZE);
    }

    public PillarEpochHistoryList getPillarsHistoryByEpoch(int epoch, int pageIndex, int pageSize) {
        JPillarEpochHistoryList response = this.client.sendRequest("embedded.pillar.getPillarsHistoryByEpoch",
                new Object[] { epoch, pageIndex, pageSize }, JPillarEpochHistoryList.class);
        return new PillarEpochHistoryList(response);
    }

    // Contract methods
    public AccountBlockTemplate register(String name, Address producerAddress, Address rewardAddress) {
        return this.register(name, producerAddress, rewardAddress, 0, 100);
    }

    public AccountBlockTemplate register(String name, Address producerAddress, Address rewardAddress,
            int giveBlockRewardPercentage, int giveDelegateRewardPercentage) {
        throw new UnsupportedOperationException();
    }

    public AccountBlockTemplate registerLegacy(String name, Address producerAddress, Address rewardAddress,
            String publicKey, String signature) {
        return this.registerLegacy(name, producerAddress, rewardAddress, publicKey, signature, 0, 100);
    }

    public AccountBlockTemplate registerLegacy(String name, Address producerAddress, Address rewardAddress,
            String publicKey, String signature, int giveBlockRewardPercentage, int giveDelegateRewardPercentage) {
        throw new UnsupportedOperationException();
    }

    public AccountBlockTemplate updatePillar(String name, Address producerAddress, Address rewardAddress,
            int giveBlockRewardPercentage, int giveDelegateRewardPercentage) {
        throw new UnsupportedOperationException();
    }

    public AccountBlockTemplate revoke(String name) {
        throw new UnsupportedOperationException();
    }

    public AccountBlockTemplate delegate(String name) {
        throw new UnsupportedOperationException();
    }

    public AccountBlockTemplate undelegate() {
        throw new UnsupportedOperationException();
    }

    // Common contract methods
    public AccountBlockTemplate collectReward() {
        throw new UnsupportedOperationException();
    }

    public AccountBlockTemplate depositQsr(long amount) {
        throw new UnsupportedOperationException();
    }

    public AccountBlockTemplate withdrawQsr() {
        throw new UnsupportedOperationException();
    }
}
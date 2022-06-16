package network.zenon.api;

import network.zenon.client.Client;
import network.zenon.model.primitives.Address;

public class SubscribeApi {
    private final Client client;

    public SubscribeApi(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return this.client;
    }

    public String toMomentums() {
        return this.client.sendRequest("ledger.subscribe", new Object[] { "momentums" }, String.class);
    }

    public String toAllAccountBlocks() {
        return this.client.sendRequest("ledger.subscribe", new Object[] { "allAccountBlocks" }, String.class);
    }

    public String toAccountBlocksByAddress(Address address) {
        return this.client.sendRequest("ledger.subscribe",
                new Object[] { "accountBlocksByAddress", address.toString() }, String.class);
    }

    public String toUnreceivedAccountBlocksByAddress(Address address) {
        return this.client.sendRequest("ledger.subscribe",
                new Object[] { "unreceivedAccountBlocksByAddress", address.toString() }, String.class);
    }
}
package network.zenon.api;

import network.zenon.client.Client;
import network.zenon.model.json.JNetworkInfo;
import network.zenon.model.json.JOsInfo;
import network.zenon.model.json.JProcessInfo;
import network.zenon.model.json.JSyncInfo;

public class StatsApi {
    private final Client client;

    public StatsApi(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return this.client;
    }

    public JOsInfo osInfo() {
        return this.client.sendRequest("stats.osInfo", null, JOsInfo.class);
    }

    public JProcessInfo processInfo() {
        return this.client.sendRequest("stats.processInfo", null, JProcessInfo.class);
    }

    public JNetworkInfo networkInfo() {
        return this.client.sendRequest("stats.networkInfo", null, JNetworkInfo.class);
    }

    public JSyncInfo syncInfo() {
        return this.client.sendRequest("stats.syncInfo", null, JSyncInfo.class);
    }
}
package network.zenon.api;

import network.zenon.client.IClient;
import network.zenon.model.json.JNetworkInfo;
import network.zenon.model.json.JOsInfo;
import network.zenon.model.json.JProcessInfo;
import network.zenon.model.json.JSyncInfo;

public class StatsApi {
	private final IClient client;
	
    public StatsApi(IClient client) {
        this.client = client;
    }

    public IClient getClient() {
    	return this.client;
    }

    public JOsInfo osInfo()
    {
        return this.client.sendRequest("stats.osInfo", null, JOsInfo.class);
    }

    public JProcessInfo processInfo()
    {
        return this.client.sendRequest("stats.processInfo", null, JProcessInfo.class);
    }

    public JNetworkInfo networkInfo()
    {
        return this.client.sendRequest("stats.networkInfo", null, JNetworkInfo.class);
    }

    public JSyncInfo syncInfo()
    {
        return this.client.sendRequest("stats.syncInfo", null, JSyncInfo.class);
    }
}
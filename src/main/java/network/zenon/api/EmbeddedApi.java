package network.zenon.api;

import network.zenon.api.embedded.AcceleratorApi;
import network.zenon.api.embedded.HtlcApi;
import network.zenon.api.embedded.PillarApi;
import network.zenon.api.embedded.PlasmaApi;
import network.zenon.api.embedded.SentinelApi;
import network.zenon.api.embedded.StakeApi;
import network.zenon.api.embedded.SwapApi;
import network.zenon.api.embedded.TokenApi;
import network.zenon.client.Client;

public class EmbeddedApi {
    private final Client client;
    private final PillarApi pillar;
    private final PlasmaApi plasma;
    private final SentinelApi sentinel;
    private final StakeApi stake;
    private final SwapApi swap;
    private final TokenApi token;
    private final AcceleratorApi accelerator;
    private final HtlcApi htlc;

    public EmbeddedApi(Client client) {
        this.client = client;

        this.pillar = new PillarApi(client);
        this.plasma = new PlasmaApi(client);
        this.sentinel = new SentinelApi(client);
        this.stake = new StakeApi(client);
        this.swap = new SwapApi(client);
        this.token = new TokenApi(client);
        this.accelerator = new AcceleratorApi(client);
        this.htlc = new HtlcApi(client);
    }

    public Client getClient() {
        return this.client;
    }

    public PillarApi getPillar() {
        return this.pillar;
    }

    public PlasmaApi getPlasma() {
        return this.plasma;
    }

    public SentinelApi getSentinel() {
        return this.sentinel;
    }

    public StakeApi getStake() {
        return this.stake;
    }

    public SwapApi getSwap() {
        return this.swap;
    }

    public TokenApi getToken() {
        return this.token;
    }

    public AcceleratorApi getAccelerator() {
        return this.accelerator;
    }

    public HtlcApi getHtlc() {
        return this.htlc;
    }
}
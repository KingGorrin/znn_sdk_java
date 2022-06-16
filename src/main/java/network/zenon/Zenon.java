package network.zenon;

import network.zenon.api.*;
import network.zenon.client.WsClient;

public class Zenon {
    private static Zenon instance;
    
    private static Zenon getInstance() {
        if (instance == null) {
            synchronized(Zenon.class) {
                if (instance == null)
                    instance = new Zenon();
            }
        }
        return instance;
    }
    
    public static WsClient getClient() {
        return getInstance().client;
    }
    
    public static LedgerApi getLedger() {
        return getInstance().ledger;
    }
    
    public static StatsApi getStats() {
        return getInstance().stats;
    }
    
    public static EmbeddedApi getEmbedded() {
        return getInstance().embedded;
    }
    
    public static SubscribeApi getSubscribe() {
        return getInstance().subscribe;
    }
    
    private final WsClient client;
    private final LedgerApi ledger;
    private final StatsApi stats;
    private final EmbeddedApi embedded;
    private final SubscribeApi subscribe;
    
    private Zenon() {
        this.client = new WsClient();
        this.ledger = new LedgerApi(this.client);
        this.stats = new StatsApi(this.client);
        this.embedded = new EmbeddedApi(this.client);
        this.subscribe = new SubscribeApi(this.client);
    }
}

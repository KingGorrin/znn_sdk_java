package network.zenon;

import java.nio.file.Path;
import java.util.function.Consumer;

import network.zenon.api.EmbeddedApi;
import network.zenon.api.LedgerApi;
import network.zenon.api.StatsApi;
import network.zenon.api.SubscribeApi;
import network.zenon.client.WsClient;
import network.zenon.model.nom.AccountBlockTemplate;
import network.zenon.pow.PowStatus;
import network.zenon.utils.BlockUtils;
import network.zenon.wallet.KeyPair;
import network.zenon.wallet.KeyStore;
import network.zenon.wallet.KeyStoreManager;

public class Zenon {
    private static Zenon instance;

    public static Zenon getInstance() {
        if (instance == null) {
            synchronized (Zenon.class) {
                if (instance == null)
                    instance = new Zenon();
            }
        }
        return instance;
    }

    private int networkIdentifier;
    private int chainIdentifier;
    
    public KeyPair defaultKeyPair;
    public KeyStore defaultKeyStore;
    public Path defaultKeyStorePath;
    
    private final KeyStoreManager keyStoreManager;
    private final WsClient client;
    private final LedgerApi ledger;
    private final StatsApi stats;
    private final EmbeddedApi embedded;
    private final SubscribeApi subscribe;

    private Zenon() {
        this.networkIdentifier = Constants.NET_ID;
        this.chainIdentifier = Constants.CHAIN_ID;
        
        this.keyStoreManager = new KeyStoreManager(Constants.ZNN_DEFAILT_WALLET_DIRECTORY);
        this.client = new WsClient();
        this.ledger = new LedgerApi(this.client);
        this.stats = new StatsApi(this.client);
        this.embedded = new EmbeddedApi(this.client);
        this.subscribe = new SubscribeApi(this.client);
    }

    public int getNetworkIdentifier() {
        return this.networkIdentifier;
    }

    public int getChainIdentifier() {
        return this.chainIdentifier;
    }
    
    public void setChainIdentifier(int value) {
         this.chainIdentifier = value;
    }

    public KeyStoreManager getKeyStoreManager() {
        return this.keyStoreManager;
    }

    public WsClient getClient() {
        return this.client;
    }

    public LedgerApi getLedger() {
        return this.ledger;
    }

    public StatsApi getStats() {
        return this.stats;
    }

    public EmbeddedApi getEmbedded() {
        return this.embedded;
    }

    public SubscribeApi getSubscribe() {
        return this.subscribe;
    }

    public AccountBlockTemplate send(AccountBlockTemplate transaction) throws Exception {
        return send(transaction, defaultKeyPair, (x) -> {
        }, false);
    }

    public AccountBlockTemplate send(AccountBlockTemplate transaction, boolean waitForRequiredPlasma) throws Exception {
        return send(transaction, defaultKeyPair, (x) -> {
        }, waitForRequiredPlasma);
    }

    public AccountBlockTemplate send(AccountBlockTemplate transaction, Consumer<PowStatus> generatingPowCallback,
            boolean waitForRequiredPlasma) throws Exception {
        return send(transaction, defaultKeyPair, generatingPowCallback, waitForRequiredPlasma);
    }

    public AccountBlockTemplate send(AccountBlockTemplate transaction, KeyPair currentKeyPair,
            Consumer<PowStatus> generatingPowCallback, boolean waitForRequiredPlasma) throws Exception {
        KeyPair keypair = currentKeyPair == null ? defaultKeyPair : currentKeyPair;

        if (keypair == null)
            throw new ZnnSdkException("No default keyPair selected");

        return BlockUtils.send(transaction, keypair, generatingPowCallback, waitForRequiredPlasma);
    }

    public boolean requiresPoW(AccountBlockTemplate transaction) {
        return requiresPoW(transaction, null);
    }

    public boolean requiresPoW(AccountBlockTemplate transaction, KeyPair currentKeyPair) {
        KeyPair keypair = currentKeyPair == null ? defaultKeyPair : currentKeyPair;

        if (keypair == null)
            throw new ZnnSdkException("No default keyPair selected");

        return BlockUtils.requiresPoW(transaction, keypair);
    }
}

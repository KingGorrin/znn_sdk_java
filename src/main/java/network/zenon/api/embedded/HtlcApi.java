package network.zenon.api.embedded;

import network.zenon.client.Client;
import network.zenon.embedded.Definitions;
import network.zenon.model.embedded.HtlcInfo;
import network.zenon.model.embedded.json.JHtlcInfo;
import network.zenon.model.nom.AccountBlockTemplate;
import network.zenon.model.primitives.Address;
import network.zenon.model.primitives.Hash;
import network.zenon.model.primitives.TokenStandard;

public class HtlcApi {
    private final Client client;

    public HtlcApi(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return this.client;
    }

    public HtlcInfo getHtlcInfoById(Hash id) {
        JHtlcInfo response = this.client.sendRequest("embedded.htlc.getHtlcInfoById", new Object[] { id.toString() },
                JHtlcInfo.class);
        return new HtlcInfo(response);
    }
    
    public boolean getHtlcProxyUnlockStatus(Address address) {
        return this.client.sendRequest("embedded.htlc.getHtlcProxyUnlockStatus", new Object[] { address.toString() }, boolean.class);
    }
    
    // Contract methods
    public AccountBlockTemplate create(TokenStandard tokenStandard,
            long amount, Address hashLocked, long expirationTime, int hashType, int keyMaxSize, byte[] hashLock) {
        return AccountBlockTemplate.callContract(Address.HTLC_ADDRESS, tokenStandard, amount, Definitions.HTLC
                .encodeFunction("Create", hashLocked, expirationTime, hashType, keyMaxSize, hashLock));
    }

    public AccountBlockTemplate reclaim(Hash id) {
        return AccountBlockTemplate.callContract(Address.HTLC_ADDRESS, TokenStandard.ZNN_ZTS,
                0, Definitions.HTLC.encodeFunction("Reclaim", id.getBytes()));
    }

    public AccountBlockTemplate unlock(Hash id, byte[] preimage) {
        return AccountBlockTemplate.callContract(Address.HTLC_ADDRESS, TokenStandard.ZNN_ZTS,
                0, Definitions.HTLC.encodeFunction("Unlock", id.getBytes(), preimage));
    }
    
    public AccountBlockTemplate denyProxyUnlock() {
        return AccountBlockTemplate.callContract(Address.HTLC_ADDRESS, TokenStandard.ZNN_ZTS,
                0, Definitions.HTLC.encodeFunction("DenyProxyUnlock"));
    }
    
    public AccountBlockTemplate allowProxyUnlock() {
        return AccountBlockTemplate.callContract(Address.HTLC_ADDRESS, TokenStandard.ZNN_ZTS,
                0, Definitions.HTLC.encodeFunction("AllowProxyUnlock"));
    }
}

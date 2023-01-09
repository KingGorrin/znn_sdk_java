package network.zenon.api.embedded;

import network.zenon.client.Client;
import network.zenon.embedded.Definitions;
import network.zenon.model.embedded.HtlcInfo;
import network.zenon.model.embedded.HtlcInfoList;
import network.zenon.model.embedded.json.JHtlcInfo;
import network.zenon.model.embedded.json.JHtlcInfoList;
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

    public HtlcInfoList getHtlcInfosByTimeLockedAddress(Address address, int pageIndex, int pageSize) {
        JHtlcInfoList response = this.client.sendRequest("embedded.htlc.getHtlcInfosByTimeLockedAddress",
                new Object[] { address.toString(), pageIndex, pageSize }, JHtlcInfoList.class);
        return new HtlcInfoList(response);
    }

    public HtlcInfoList getHtlcInfosByHashLockedAddress(Address address, int pageIndex, int pageSize) {
        JHtlcInfoList response = this.client.sendRequest("embedded.htlc.getHtlcInfosByHashLockedAddress",
                new Object[] { address.toString(), pageIndex, pageSize }, JHtlcInfoList.class);
        return new HtlcInfoList(response);
    }

    // Contract methods
    public AccountBlockTemplate createHtlc(TokenStandard tokenStandard,
            long amount, Address hashLocked, long expirationTime, int hashType, int keyMaxSize, byte[] hashLock) {
        return AccountBlockTemplate.callContract(Address.HTLC_ADDRESS, tokenStandard, amount, Definitions.HTLC
                .encodeFunction("CreateHtlc", hashLocked, expirationTime, hashType, keyMaxSize, hashLock));
    }

    public AccountBlockTemplate reclaimHtlc(Hash id) {
        return AccountBlockTemplate.callContract(Address.HTLC_ADDRESS, TokenStandard.ZNN_ZTS,
                0, Definitions.HTLC.encodeFunction("ReclaimHtlc", id.getBytes()));
    }

    public AccountBlockTemplate unlockHtlc(Hash id, byte[] preimage) {
        return AccountBlockTemplate.callContract(Address.HTLC_ADDRESS, TokenStandard.ZNN_ZTS,
                0, Definitions.HTLC.encodeFunction("UnlockHtlc", id.getBytes(), preimage));
    }
}

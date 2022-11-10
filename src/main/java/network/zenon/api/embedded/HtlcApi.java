package network.zenon.api.embedded;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jsoniter.spi.TypeLiteral;

import network.zenon.client.Client;
import network.zenon.embedded.Definitions;
import network.zenon.model.embedded.HtlcInfo;
import network.zenon.model.embedded.PillarInfo;
import network.zenon.model.embedded.json.JHtlcInfo;
import network.zenon.model.nom.AccountBlockTemplate;
import network.zenon.model.primitives.Address;
import network.zenon.model.primitives.Hash;
import network.zenon.model.primitives.TokenStandard;
import network.zenon.utils.JsonUtils;

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

    public List<HtlcInfo> getHtlcInfosByTimeLockedAddress(Address address, int pageIndex, int pageSize) {
        Object response = this.client.sendRequest("embedded.htlc.getHtlcInfosByTimeLockedAddress",
                new Object[] { address.toString(), pageIndex, pageSize });
        List<JHtlcInfo> result = JsonUtils.deserialize(response.toString(), new TypeLiteral<List<JHtlcInfo>>() {});
        return result.stream().map(x -> new HtlcInfo(x)).collect(Collectors.toList());
    }

    public List<HtlcInfo> getHtlcInfosByHashLockedAddress(Address address, int pageIndex, int pageSize) {
        Object response = this.client.sendRequest("embedded.htlc.getHtlcInfosByHashLockedAddress",
                new Object[] { address.toString(), pageIndex, pageSize });
        List<JHtlcInfo> result = JsonUtils.deserialize(response.toString(), new TypeLiteral<List<JHtlcInfo>>() {});
        return result.stream().map(x -> new HtlcInfo(x)).collect(Collectors.toList());
    }

    // Contract methods
    public AccountBlockTemplate createHtlc(Address timeLocked, Address hashLocked, TokenStandard tokenStandard,
            long amount, long expirationTime, int hashType, int keyMaxSize, byte[] hashLock) {
        AccountBlockTemplate template = AccountBlockTemplate.callContract(Address.HTLC_ADDRESS, tokenStandard, amount, Definitions.HTLC
                .encodeFunction("CreateHtlc", hashLocked, expirationTime, hashType, keyMaxSize, hashLock));
        template.setAddress(timeLocked);
        return template;
    }

    public AccountBlockTemplate reclaimHtlc(Address timeLocked, Hash id) {
        AccountBlockTemplate template = AccountBlockTemplate.callContract(Address.HTLC_ADDRESS, TokenStandard.ZNN_ZTS,
                0, Definitions.HTLC.encodeFunction("ReclaimHtlc", id.getBytes()));
        template.setAddress(timeLocked);
        return template;
    }

    public AccountBlockTemplate unlockHtlc(Address hashLocked, Hash id, byte[] preimage) {
        AccountBlockTemplate template = AccountBlockTemplate.callContract(Address.HTLC_ADDRESS, TokenStandard.ZNN_ZTS,
                0, Definitions.HTLC.encodeFunction("UnlockHtlc", id.getBytes(), preimage));
        template.setAddress(hashLocked);
        return template;
    }
}

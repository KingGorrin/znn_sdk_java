package network.zenon.api.embedded;

import network.zenon.Constants;
import network.zenon.client.Client;
import network.zenon.embedded.Definitions;
import network.zenon.model.embedded.FusionEntryList;
import network.zenon.model.embedded.GetRequiredParam;
import network.zenon.model.embedded.GetRequiredResponse;
import network.zenon.model.embedded.PlasmaInfo;
import network.zenon.model.embedded.json.JFusionEntryList;
import network.zenon.model.embedded.json.JGetRequiredResponse;
import network.zenon.model.embedded.json.JPlasmaInfo;
import network.zenon.model.nom.AccountBlockTemplate;
import network.zenon.model.primitives.Address;
import network.zenon.model.primitives.Hash;
import network.zenon.model.primitives.TokenStandard;

public class PlasmaApi {
    private final Client client;

    public PlasmaApi(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return this.client;
    }

    public PlasmaInfo get(Address address) {
        JPlasmaInfo response = this.client.sendRequest("embedded.plasma.get", new Object[] { address.toString() },
                JPlasmaInfo.class);
        return new PlasmaInfo(response);
    }

    public FusionEntryList getEntriesByAddress(Address address) {
        return this.getEntriesByAddress(address, 0, Constants.RPC_MAX_PAGE_SIZE);
    }

    public FusionEntryList getEntriesByAddress(Address address, int pageIndex, int pageSize) {
        JFusionEntryList response = this.client.sendRequest("embedded.plasma.getEntriesByAddress",
                new Object[] { address.toString(), pageIndex, pageSize }, JFusionEntryList.class);
        return new FusionEntryList(response);
    }

    public long getPlasmaByQsr(double qsrAmount) {
        return (long) (qsrAmount * 2100);
    }

    public GetRequiredResponse getRequiredPoWForAccountBlock(GetRequiredParam powParam) {
        JGetRequiredResponse response = this.client.sendRequest("embedded.plasma.getRequiredPoWForAccountBlock",
                new Object[] { powParam.toJson() }, JGetRequiredResponse.class);
        return new GetRequiredResponse(response);
    }

    // Contract methods
    public AccountBlockTemplate fuse(Address beneficiary, long amount) {
        return AccountBlockTemplate.callContract(Address.PLASMA_ADDRESS, TokenStandard.QSR_ZTS, amount,
                Definitions.PLASMA.encodeFunction("Fuse", beneficiary));
    }

    public AccountBlockTemplate cancel(Hash id) {
        return AccountBlockTemplate.callContract(Address.PLASMA_ADDRESS, TokenStandard.ZNN_ZTS, 0,
                Definitions.PLASMA.encodeFunction("CancelFuse", id.getBytes()));
    }
}
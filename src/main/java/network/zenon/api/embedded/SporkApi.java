package network.zenon.api.embedded;

import network.zenon.client.Client;
import network.zenon.embedded.Definitions;
import network.zenon.model.embedded.SporkList;
import network.zenon.model.embedded.json.JSporkList;
import network.zenon.model.nom.AccountBlockTemplate;
import network.zenon.model.primitives.Address;
import network.zenon.model.primitives.Hash;
import network.zenon.model.primitives.TokenStandard;

public class SporkApi {
    private final Client client;

    public SporkApi(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return this.client;
    }

    public SporkList getAll(int pageIndex, int pageSize) {
        JSporkList response = this.client.sendRequest("embedded.spork.getAll", new Object[] { pageIndex, pageSize },
                JSporkList.class);
        return new SporkList(response);
    }

    // Contract methods
    public AccountBlockTemplate createSpork(String name, String description) {
        return AccountBlockTemplate.callContract(Address.SPORK_ADDRESS, TokenStandard.ZNN_ZTS, 0, Definitions.SPORK
                .encodeFunction("CreateSpork", name, description));
    }
    
    public AccountBlockTemplate activateSpork(Hash id) {
        return AccountBlockTemplate.callContract(Address.SPORK_ADDRESS, TokenStandard.ZNN_ZTS, 0, Definitions.SPORK
                .encodeFunction("ActivateSpork", id.getBytes()));
    }
}

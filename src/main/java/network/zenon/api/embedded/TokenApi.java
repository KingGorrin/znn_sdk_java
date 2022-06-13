package network.zenon.api.embedded;

import network.zenon.Constants;
import network.zenon.client.IClient;
import network.zenon.model.nom.AccountBlockTemplate;
import network.zenon.model.nom.Token;
import network.zenon.model.nom.TokenList;
import network.zenon.model.nom.json.JToken;
import network.zenon.model.nom.json.JTokenList;
import network.zenon.model.primitives.Address;
import network.zenon.model.primitives.TokenStandard;

public class TokenApi {
    private final IClient client;

    public TokenApi(IClient client) {
        this.client = client;
    }

    public IClient getClient() {
        return this.client;
    }

    public TokenList getAll() {
        return this.getAll(0, Constants.RPC_MAX_PAGE_SIZE);
    }

    public TokenList getAll(int pageIndex, int pageSize) {
        JTokenList response = this.client.sendRequest("embedded.token.getAll", new Object[] { pageIndex, pageSize },
                JTokenList.class);
        return new TokenList(response);
    }

    public TokenList getByOwner(Address address) {
        return this.getByOwner(address, 0, Constants.RPC_MAX_PAGE_SIZE);
    }

    public TokenList getByOwner(Address address, int pageIndex, int pageSize) {
        JTokenList response = this.client.sendRequest("embedded.token.getByOwner",
                new Object[] { address.toString(), pageIndex, pageSize }, JTokenList.class);
        return new TokenList(response);
    }

    public Token getByZts(TokenStandard tokenStandard) {
        JToken response = this.client.sendRequest("embedded.token.getByZts", new Object[] { tokenStandard.toString() },
                JToken.class);
        return response != null ? new Token(response) : null;
    }

    // Contract methods
    public AccountBlockTemplate issueToken(String tokenName, String tokenSymbol, String tokenDomain, long totalSupply,
            long maxSupply, int decimals, boolean mintable, boolean burnable, boolean utility) {
        throw new UnsupportedOperationException();
    }

    public AccountBlockTemplate mintToken(TokenStandard tokenStandard, long amount, Address receiveAddress) {
        throw new UnsupportedOperationException();
    }

    public AccountBlockTemplate burnToken(TokenStandard tokenStandard, long amount) {
        throw new UnsupportedOperationException();
    }

    public AccountBlockTemplate updateToken(TokenStandard tokenStandard, Address owner, boolean isMintable,
            boolean isBurnable) {
        throw new UnsupportedOperationException();
    }
}

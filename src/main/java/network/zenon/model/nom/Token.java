package network.zenon.model.nom;

import com.jsoniter.output.JsonStream;

import network.zenon.model.IJsonConvertible;
import network.zenon.model.nom.json.JToken;
import network.zenon.model.primitives.Address;
import network.zenon.model.primitives.TokenStandard;

public class Token implements IJsonConvertible<JToken> {
    private final String name;
    private final String symbol;
    private final String domain;
    private final long totalSupply;
    private final long decimals;
    private final Address owner;
    private final TokenStandard tokenStandard;
    private final long maxSupply;
    private final boolean isBurnable;
    private final boolean isMintable;
    private final boolean isUtility;

    public Token(JToken json) {
        this.name = json.name;
        this.symbol = json.symbol;
        this.domain = json.domain;
        this.totalSupply = json.totalSupply;
        this.decimals = json.decimals;
        this.owner = Address.parse(json.owner);
        this.tokenStandard = TokenStandard.parse(json.tokenStandard);
        this.maxSupply = json.maxSupply;
        this.isBurnable = json.isBurnable;
        this.isMintable = json.isMintable;
        this.isUtility = json.isUtility;
    }

    public Token(String name, String symbol, String domain, long totalSupply, long decimals, Address owner,
            TokenStandard tokenStandard, long maxSupply, boolean isBurnable, boolean isMintable, boolean isUtility) {
        this.name = name;
        this.symbol = symbol;
        this.domain = domain;
        this.totalSupply = totalSupply;
        this.decimals = decimals;
        this.owner = owner;
        this.tokenStandard = tokenStandard;
        this.maxSupply = maxSupply;
        this.isBurnable = isBurnable;
        this.isMintable = isMintable;
        this.isUtility = isUtility;
    }

    public String getName() {
        return this.name;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public String getDomain() {
        return this.domain;
    }

    public long getTotalSupply() {
        return this.totalSupply;
    }

    public long getDecimals() {
        return this.decimals;
    }

    public Address getOwner() {
        return this.owner;
    }

    public TokenStandard getTokenStandard() {
        return this.tokenStandard;
    }

    public long getMaxSupply() {
        return this.maxSupply;
    }

    public boolean getIsBurnable() {
        return this.isBurnable;
    }

    public boolean getIsMintable() {
        return this.isMintable;
    }

    public boolean getIsUtility() {
        return this.isUtility;
    }

    @Override
    public JToken toJson() {
        JToken json = new JToken();
        json.name = this.name;
        json.symbol = this.symbol;
        json.domain = this.domain;
        json.totalSupply = this.totalSupply;
        json.decimals = this.decimals;
        json.owner = this.owner.toString();
        json.tokenStandard = this.tokenStandard.toString();
        json.maxSupply = this.maxSupply;
        json.isBurnable = this.isBurnable;
        json.isMintable = this.isMintable;
        json.isUtility = this.isUtility;
        return json;
    }

    @Override
    public String toString() {
        return JsonStream.serialize(this.toJson());
    }

    @Override
    public int hashCode() {
        return this.tokenStandard.toString().hashCode();
    }

    public int getDecimalsExponent() {
        return (int) Math.pow(10.0, this.decimals);
    }

    @Override
    public boolean equals(Object obj) {
        return this.equals((Token) obj);
    }

    public boolean Equals(Token other) {
        return this.tokenStandard.equals(other.tokenStandard);
    }
}
package network.zenon.model.nom;

import com.jsoniter.output.JsonStream;

import network.zenon.model.IJsonConvertible;
import network.zenon.model.nom.json.JBalanceInfoListItem;
import network.zenon.utils.AmountUtils;

public class BalanceInfoListItem implements IJsonConvertible<JBalanceInfoListItem> {
	private final Token token;
	private final Long balance;
	private final Double balanceWithDecimals;
	private final String balanceFormatted;
    
    public BalanceInfoListItem(JBalanceInfoListItem json) {
        this.token = json.token != null ? new Token(json.token) : null;
        this.balance = json.balance;
        this.balanceWithDecimals = AmountUtils.addDecimals(balance.longValue(), this.token.getDecimals());
        this.balanceFormatted = this.balanceWithDecimals.toString() + " " + this.token.getSymbol();
    }

    public BalanceInfoListItem(Token token, Long balance) {
        this.token = token;
        this.balance = balance;
        this.balanceWithDecimals = AmountUtils.addDecimals(balance.longValue(), this.token.getDecimals());
        this.balanceFormatted = this.balanceWithDecimals.toString() + " " + this.token.getSymbol();
    }

    public Token getToken() {
    	return this.token;
    }
    
    public Long getBalance() {
    	return this.balance;
    }
    
    public Double getBalanceWithDecimals() {
    	return this.balanceWithDecimals;
    }
    
    public String getBalanceFormatted() {
    	return this.balanceFormatted;
    }

    @Override
    public JBalanceInfoListItem toJson() {
    	JBalanceInfoListItem json = new JBalanceInfoListItem();
        json.token = this.token != null ? this.token.toJson() : null;
        json.balance = this.balance;
        return json;
    }
    
    @Override
    public String toString() {
    	return JsonStream.serialize(this.toJson());
    }
}
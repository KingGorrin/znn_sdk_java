package network.zenon.model.nom;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import network.zenon.model.JsonConvertible;
import network.zenon.model.nom.json.JAccountInfo;
import network.zenon.model.primitives.TokenStandard;
import network.zenon.utils.JsonUtils;

public class AccountInfo implements JsonConvertible<JAccountInfo> {
    private final String address;
    private final Long blockCount;
    private final List<BalanceInfoListItem> balanceInfoList;

    public AccountInfo(JAccountInfo json) {
        this.address = json.address;
        this.blockCount = json.accountHeight;
        this.balanceInfoList = this.blockCount > 0
                ? Collections.unmodifiableList(json.balanceInfoMap.values().stream()
                        .map(x -> new BalanceInfoListItem(x)).collect(Collectors.toList()))
                : Collections.emptyList();
    }

    public AccountInfo(String address, Long blockCount, List<BalanceInfoListItem> balanceInfoList) {
        this.address = address;
        this.blockCount = blockCount;
        this.balanceInfoList = Collections.unmodifiableList(balanceInfoList);
    }

    public String getAddress() {
        return this.address;
    }

    public Long getBlockCount() {
        return this.blockCount;
    }

    public List<BalanceInfoListItem> getBalanceInfoList() {
        return this.balanceInfoList;
    }

    public long getZnn() {
        return this.getBalance(TokenStandard.ZNN_ZTS);
    }

    public long getQsr() {
        return this.getBalance(TokenStandard.QSR_ZTS);
    }

    public long getBalance(TokenStandard tokenStandard) {
        BalanceInfoListItem info = this.balanceInfoList.stream()
                .filter(x -> x.getToken().getTokenStandard().equals(tokenStandard)).findFirst().orElse(null);

        return info != null ? info.getBalance() : 0;
    }

    public double getBalanceWithDecimals(TokenStandard tokenStandard) {
        BalanceInfoListItem info = this.balanceInfoList.stream()
                .filter(x -> x.getToken().getTokenStandard().equals(tokenStandard)).findFirst().orElse(null);

        return info != null ? info.getBalanceWithDecimals() : 0;
    }

    public Token findTokenByTokenStandard(TokenStandard tokenStandard) {
        BalanceInfoListItem info = this.balanceInfoList.stream()
                .filter(x -> x.getToken().getTokenStandard().equals(tokenStandard)).findFirst().orElse(null);

        return info != null ? info.getToken() : null;
    }

    @Override
    public JAccountInfo toJson() {
        JAccountInfo json = new JAccountInfo();
        json.address = this.address.toString();
        json.accountHeight = this.blockCount;
        json.balanceInfoMap = this.balanceInfoList.stream()
                .collect(Collectors.toMap(x -> x.getToken().getTokenStandard().toString(), y -> y.toJson()));
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}
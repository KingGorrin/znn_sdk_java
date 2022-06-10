package network.zenon.model.nom.json;

import java.util.Map;

public class JAccountInfo {
    public String address;
    public Long accountHeight;
    public Map<String, JBalanceInfoListItem> balanceInfoMap;
}
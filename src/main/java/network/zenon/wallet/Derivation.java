package network.zenon.wallet;

/// <summary>
/// BIP44 https://github.com/bitcoin/bips/blob/master/bip-0044.mediawiki
/// </summary>
/// m / purpose' / coin_type' / account' / change / address_index
public final class Derivation {
    public static final String COIN_TYPE = "73404";
    public static final String DERIVATION_PATH = "m/44'/" + COIN_TYPE + "'";

    public static String getDerivationAccount() {
        return getDerivationAccount(0);
    }

    public static String getDerivationAccount(int account) {
        return String.format("%s/%s'", DERIVATION_PATH, account);
    }
}
package network.zenon.wallet;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import io.github.novacrypto.bip39.MnemonicGenerator;
import io.github.novacrypto.bip39.MnemonicValidator;
import io.github.novacrypto.bip39.SeedCalculator;
import io.github.novacrypto.bip39.wordlists.English;
import network.zenon.crypto.Crypto;
import network.zenon.model.primitives.Address;
import network.zenon.utils.BytesUtils;

public class KeyStore {
    public static KeyStore fromMnemonic(String mnemonic) {
        try {
            byte[] entropy = MnemonicValidator.ofWordList(English.INSTANCE).validate(mnemonic);
            byte[] seed = new SeedCalculator().calculateSeed(mnemonic, "");

            return new KeyStore(mnemonic, BytesUtils.toHexString(entropy), BytesUtils.toHexString(seed));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid mnemonic.");
        }
    }

    public static KeyStore fromSeed(String seed) {
        return new KeyStore(null, null, seed);
    }

    public static KeyStore fromEntropy(String entropy) {
        StringBuilder sb = new StringBuilder();
        new MnemonicGenerator(English.INSTANCE).createMnemonic(entropy, sb::append);
        return fromMnemonic(sb.toString());
    }

    public static KeyStore newRandom() {
        byte[] entropy = new byte[32];
        new SecureRandom().nextBytes(entropy);
        return fromEntropy(BytesUtils.toHexString(entropy));
    }

    private final String mnemonic;
    private final String entropy;
    private final String seed;

    private KeyStore(String mnemonic, String entropy, String seed) {
        this.mnemonic = mnemonic;
        this.entropy = entropy;
        this.seed = seed;
    }

    public String getMnemonic() {
        return this.mnemonic;
    }

    public String getEntropy() {
        return this.entropy;
    }

    public String getSeed() {
        return this.seed;
    }

    public KeyPair getKeyPair() {
        return getKeyPair(0);
    }

    public KeyPair getKeyPair(int index) {
        return new KeyPair(
                Crypto.deriveKey(Derivation.getDerivationAccount(index), BytesUtils.fromHexString(this.seed)));
    }

    public Address[] deriveAddressesByRange(int left, int right) {
        List<Address> addresses = new ArrayList<>();
        if (this.seed != null) {
            for (int i = left; i < right; i++) {
                addresses.add(this.getKeyPair(i).getAddress());
            }
        }
        return addresses.toArray(new Address[0]);
    }

    public FindResponse findAddress(Address address, int numOfAddresses) {
        for (int i = 0; i < numOfAddresses; i++) {
            KeyPair pair = this.getKeyPair(i);
            if (pair.getAddress().equals(address)) {
                return new FindResponse(null, i, pair);
            }
        }
        return null;
    }
}

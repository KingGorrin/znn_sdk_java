package network.zenon.wallet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import network.zenon.model.primitives.Address;
import network.zenon.utils.BytesUtils;

public class KeyStoreTest {

    public static final String MNEMONIC = 
            "route become dream access impulse price inform obtain engage ski believe awful absent pig thing vibrant possible exotic flee pepper marble rural fire fancy";
    public static final String ENTROPY = "bc827d0a00a72354dce4c44a59485288500b49382f9ba88a016351787b7b15ca";
    public static final String SEED = "19f1d107d49f42ebc14d46b51001c731569f142590fdd20167ddeedbb201516731ad5ac9b58d3a1c9c09debfe62538379461e4ea9f038124c428784fecc645b7";
    
    private static Stream<Arguments> keyStoreDerivationAccountTestData() {
        KeyStore keyStore = KeyStore.fromMnemonic(MNEMONIC);

        return Stream.of(
                // Test Boolean
                Arguments.of(keyStore, 0, 
                        new KeyPair(
                                BytesUtils.fromHexString("d6b01f96b566d7df9b5b53b1971e4baeb74cc64167a9843f82d04b2194ca4863"),
                                BytesUtils.fromHexString("3e13d7238d0e768a567dce84b54915f2323f2dcd0ef9a716d9c61abed631ba10"), 
                                Address.parse("z1qqjnwjjpnue8xmmpanz6csze6tcmtzzdtfsww7"))),
                Arguments.of(keyStore, 1, 
                        new KeyPair(
                                BytesUtils.fromHexString("bd14c955a2e67246dd8f273127a124ef97b869ef1301378c44760f96b426ee18"),
                                BytesUtils.fromHexString("fb6416d170dda0b2a2857d8460f746c9639522cf2255ed2efcd54f6337bd718e"), 
                                Address.parse("z1qr44l6ajstm5gfrvwtsrfg446y6mcv8r60v090"))),
                Arguments.of(keyStore, 2, 
                        new KeyPair(
                                BytesUtils.fromHexString("1fc9a73b1838f29f86f9221e51051020b16a81034f92950cb8d486d04f12de37"),
                                BytesUtils.fromHexString("e253210b1bb64a9f3a606cacc988bc22f123c590507e3b8a076ec3923f41d6c1"), 
                                Address.parse("z1qqk6gsd2sv49azqrzxqe8q6jjssufa35mry83t"))),
                Arguments.of(keyStore, 3, 
                        new KeyPair(
                                BytesUtils.fromHexString("67f9700dc8bd0b61c2ae76f115655f6f06a27b8fad38ec98e562911d0859bdd8"),
                                BytesUtils.fromHexString("124a2d88293f098d3b37de80e58bd5fbc3b584a5169354983a70dd8a9bd7c56e"), 
                                Address.parse("z1qr5kzrlrw4e4stkcylcynwv5rja8ksh6qvm2ul"))),
                Arguments.of(keyStore, 4, 
                        new KeyPair(
                                BytesUtils.fromHexString("97fc1b2fd3daedd9f38bb5f11cf22d6cc767a2e976b4c9422b7346a68a62a7e9"),
                                BytesUtils.fromHexString("6fd663e4c66201718611d26d0633381f07c23f6a08862e07c0b718e090dc1366"), 
                                Address.parse("z1qq8gh5vdezsara9p2t7p0m7dvplm2sjzq0nr3d"))));
    }
    
    @Test
    public void validateKeyStoreFromMnemonic()
    {
        // Execute
        KeyStore keyStore = KeyStore.fromMnemonic(MNEMONIC);
        
        // Validate
        assertEquals(keyStore.getMnemonic(), MNEMONIC);
        assertEquals(keyStore.getEntropy(), ENTROPY);
        assertEquals(keyStore.getSeed(), SEED);
    }
    
    @Test
    public void validateKeyStoreFromEntropy()
    {
        // Execute
        KeyStore keyStore = KeyStore.fromEntropy(ENTROPY);
        
        // Validate
        assertEquals(keyStore.getMnemonic(), MNEMONIC);
        assertEquals(keyStore.getEntropy(), ENTROPY);
        assertEquals(keyStore.getSeed(), SEED);
    }
    
    @Test
    public void validateKeyStoreFromSeed()
    {
        // Execute
        KeyStore keyStore = KeyStore.fromSeed(SEED);
        
        // Validate
        assertEquals(keyStore.getMnemonic(), null);
        assertEquals(keyStore.getEntropy(), null);
        assertEquals(keyStore.getSeed(), SEED);
    }
    
    @ParameterizedTest
    @MethodSource(value = "keyStoreDerivationAccountTestData")
    public void whenDeriveAccountExpectToEqual(KeyStore keyStore, int index, KeyPair expectedResult) {
        // Execute
        KeyPair result = keyStore.getKeyPair(index);

        // Validate
        assertEquals(BytesUtils.toHexString(expectedResult.getPrivateKey()), BytesUtils.toHexString(result.getPrivateKey()));
        assertEquals(BytesUtils.toHexString(expectedResult.getPublicKey()), BytesUtils.toHexString(result.getPublicKey()));
        assertEquals(expectedResult.getAddress(), result.getAddress());
    }
    
    @Test
    public void whenDeriveAddressByRangeExpectResult() {
        // Setup
        Address[] expectedResult = new Address[] {
                Address.parse("z1qr44l6ajstm5gfrvwtsrfg446y6mcv8r60v090"),
                Address.parse("z1qqk6gsd2sv49azqrzxqe8q6jjssufa35mry83t"),
                Address.parse("z1qr5kzrlrw4e4stkcylcynwv5rja8ksh6qvm2ul")
        };
        
        KeyStore keyStore = KeyStore.fromMnemonic(MNEMONIC);
        
        // Execute
        Address[] result = keyStore.deriveAddressesByRange(1, 4);
        
        // Validate
        assertArrayEquals(expectedResult, result);
    }
    
    @Test
    public void whenFindAddressExpectResult() {
        // Setup
        KeyStore keyStore = KeyStore.fromMnemonic(MNEMONIC);
        
        // Execute
        FindResponse result = keyStore.findAddress(Address.parse("z1qq8gh5vdezsara9p2t7p0m7dvplm2sjzq0nr3d"), 10);
        
        // Validate
        assertNotNull(result);
    }
}

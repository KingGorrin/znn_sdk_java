package network.zenon.wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import network.zenon.wallet.json.JArgon2Params;
import network.zenon.wallet.json.JCryptoData;
import network.zenon.wallet.json.JKeyFile;

public class KeyFileTest {
    public static final String MNEMONIC = 
            "route become dream access impulse price inform obtain engage ski believe awful absent pig thing vibrant possible exotic flee pepper marble rural fire fancy";

    public static final JKeyFile KEY_FILE_JSON;
    
    static {
        JKeyFile keyFile = new JKeyFile();
        keyFile.baseAddress = "z1qqjnwjjpnue8xmmpanz6csze6tcmtzzdtfsww7";
        keyFile.crypto = new JCryptoData();
        keyFile.crypto.argon2Params = new JArgon2Params();
        keyFile.crypto.argon2Params.salt = "0x22a9a6a23a93000f165c787d0b09bfdb";
        keyFile.crypto.cipherData = "0x7661b6e00a93aefd5606d454adca5763efd90ab04aea31ee3b2a036eb117b0d95c66a0d709aa9827ec18485f194283e5";
        keyFile.crypto.cipherName = "aes-256-gcm";
        keyFile.crypto.kdf = "argon2.IDKey";
        keyFile.crypto.nonce = "0x6a2708a6b5a40a683e6f35cc";
        keyFile.timestamp = 1657541141;
        keyFile.version = 1;
        KEY_FILE_JSON = keyFile;
    }
    
    @Test
    public void whenEncryptExpectToEqual() throws Exception
    {
        // Setup
        KeyStore originalKeyStore = KeyStore.fromMnemonic(MNEMONIC);
        
        // Execute
        KeyFile keyFile = KeyFile.encrypt(originalKeyStore, "Secret");
        keyFile = new KeyFile(keyFile.toJson());
        KeyStore decryptedKeyStore = keyFile.decrypt("Secret");
        
        // Validate
        assertEquals(originalKeyStore.getEntropy(), decryptedKeyStore.getEntropy());
        assertEquals(originalKeyStore.getMnemonic(), decryptedKeyStore.getMnemonic());
        assertEquals(originalKeyStore.getSeed(), decryptedKeyStore.getSeed());
    }
    
    @Test
    public void whenDecryptExpectToEqual() throws Exception
    {
        // Setup
        KeyStore originalKeyStore = KeyStore.fromMnemonic(MNEMONIC);
        KeyFile keyFile = new KeyFile(KEY_FILE_JSON);
        
        // Execute
        KeyStore decryptedKeyStore = keyFile.decrypt("Secret");
        
        // Validate
        assertEquals(originalKeyStore.getEntropy(), decryptedKeyStore.getEntropy());
        assertEquals(originalKeyStore.getMnemonic(), decryptedKeyStore.getMnemonic());
        assertEquals(originalKeyStore.getSeed(), decryptedKeyStore.getSeed());
    }
}

package network.zenon.wallet;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import network.zenon.utils.BytesUtils;

public class KeyPairTest {
    
    public static final String MNEMONIC = 
            "route become dream access impulse price inform obtain engage ski believe awful absent pig thing vibrant possible exotic flee pepper marble rural fire fancy";
    
    public static final String MESSAGE = "Hello Zenon!";
    public static final String MESSAGE_SIGNATURE = "248591c0de1348336070564c2f1a43fad5529bf24fef9e4fe23298d55a2f59908bfa330b680ee170e6df3e5713bd7792f304620534cb9274b065052b84f5be0a";
    
    @Test
    public void whenSignMessageExpectToEqual()
    {
        // Setup
        byte[] expectedSignature = BytesUtils.fromHexString(MESSAGE_SIGNATURE);
        KeyStore keyStore = KeyStore.fromMnemonic(MNEMONIC);
        KeyPair keyPair = keyStore.getKeyPair();
        
        // Execute
        byte[] actualSignature = keyPair.sign(MESSAGE.getBytes());
        
        // Validate
        assertArrayEquals(expectedSignature, actualSignature);
    }
    
    @Test
    public void whenVerifyMessageExpectValid()
    {
        // Setup
        byte[] signature = BytesUtils.fromHexString(MESSAGE_SIGNATURE);
        KeyStore keyStore = KeyStore.fromMnemonic(MNEMONIC);
        KeyPair keyPair = keyStore.getKeyPair();
        
        // Execute
        boolean valid  = keyPair.verify(signature, MESSAGE.getBytes());
        
        // Validate
        assertTrue(valid);
    }
    
    @Test
    public void whenVerifyMessageExpectInvalid()
    {
        // Setup
        byte[] signature = BytesUtils.fromHexString(MESSAGE_SIGNATURE);
        KeyStore keyStore = KeyStore.newRandom();
        KeyPair keyPair = keyStore.getKeyPair();
        
        // Execute
        boolean valid  = keyPair.verify(signature, MESSAGE.getBytes());
        
        // Validate
        assertFalse(valid);
    }
}

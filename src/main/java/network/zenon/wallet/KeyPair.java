package network.zenon.wallet;

import network.zenon.crypto.Crypto;
import network.zenon.model.primitives.Address;

public class KeyPair {
    private final byte[] privateKey;
    private final byte[] publicKey;
    private final Address address;

    public KeyPair(byte[] privateKey) {
        this(privateKey, null, null);
    }

    public KeyPair(byte[] privateKey, byte[] publicKey) {
        this(privateKey, publicKey, null);
    }

    public KeyPair(byte[] privateKey, byte[] publicKey, Address address) {
        if (privateKey == null)
            throw new IllegalArgumentException("Private key cannot be null.");

        this.privateKey = privateKey;
        this.publicKey = publicKey == null ? Crypto.getPublicKey(privateKey) : publicKey;
        this.address = address == null ? Address.fromPublicKey(this.publicKey) : address;
    }

    public byte[] getPrivateKey() {
        return this.privateKey;
    }

    public byte[] getPublicKey() {
        return this.publicKey;
    }

    public Address getAddress() {
        return this.address;
    }

    public byte[] sign(byte[] message) {
        return Crypto.sign(message, this.getPrivateKey(), this.getPublicKey());
    }

    public boolean verify(byte[] signature, byte[] message) {
        return Crypto.verify(signature, message, this.getPublicKey());
    }

    public byte[] generatePublicKey(byte[] privateKey) {
        return Crypto.getPublicKey(privateKey);
    }
}

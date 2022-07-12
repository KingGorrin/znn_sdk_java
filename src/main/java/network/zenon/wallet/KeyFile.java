package network.zenon.wallet;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import de.mkammerer.argon2.Argon2Advanced;
import de.mkammerer.argon2.Argon2Factory;
import network.zenon.model.primitives.Address;
import network.zenon.utils.BytesUtils;
import network.zenon.utils.JsonUtils;
import network.zenon.wallet.json.JArgon2Params;
import network.zenon.wallet.json.JCryptoData;
import network.zenon.wallet.json.JKeyFile;

public class KeyFile {
    public static final int GCM_TAG_LENGTH = 16;
    public static final int GCM_IV_LENGTH = 12;

    public static KeyFile encrypt(KeyStore store, String password) throws Exception {
        long epoch = System.currentTimeMillis() / 1000L;
        long now = System.currentTimeMillis() - epoch;
        int timestamp = Math.round(now / 1000);

        // Salt (16 bytes recommended for password hashing)
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        byte[] entropy = BytesUtils.fromHexString(store.getEntropy());
        byte[] key = Argon2Utils.hash(password.getBytes(), salt);
        byte[] IV = new byte[GCM_IV_LENGTH];
        new SecureRandom().nextBytes(IV);

        try {
            byte[] cipherData = AesGcmUtils.encrypt(entropy, key, IV);

            return new KeyFile(store.getKeyPair().getAddress(),
                    new CryptoData(new Argon2Params(salt), cipherData, "aes-256-gcm", "argon2.IDKey", IV), timestamp,
                    1);
        } catch (NoSuchAlgorithmException ex) {
            throw new UnsupportedOperationException("AES-GCM Encryption is not supported on this platform.");
        }
    }

    public KeyStore decrypt(String password) throws Exception {
        byte[] cipherData = this.crypto.cipherData;
        byte[] key = Argon2Utils.hash(password.getBytes(), this.crypto.argon2Params.salt);
        byte[] IV = this.crypto.nonce;

        try {
            byte[] entropy = AesGcmUtils.decrypt(cipherData, key, IV);

            return KeyStore.fromEntropy(BytesUtils.toHexString(entropy));
        } catch (NoSuchAlgorithmException ex) {
            throw new UnsupportedOperationException("AES-GCM Encryption is not supported on this platform.");
        }
    }

    private static byte[] fromHexString(String value) {
        return BytesUtils.fromHexString(value.substring(2));
    }

    private static String toHexString(byte[] bytes) {
        return "0x" + BytesUtils.toHexString(bytes);
    }

    private static class Argon2Utils {
        public static byte[] hash(byte[] password, byte[] salt) {
            // Create password hash using Argon2id hashing algorithm.
            Argon2Advanced argon2 = Argon2Factory.createAdvanced(Argon2Factory.Argon2Types.ARGON2id, salt.length, 32);

            return argon2.rawHash(1, // Number of iterations to perform
                    64 * 1024, // Amount of memory (in kilobytes) to use
                    4, // Degree of parallelism (i.e. number of threads)
                    password, salt);
        }
    }

    private static class AesGcmUtils {
        public static byte[] encrypt(byte[] input, byte[] key, byte[] IV) throws Exception {
            // Get Cipher Instance
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            // Create SecretKeySpec
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");

            // Create GCMParameterSpec
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);

            // Initialize Cipher for ENCRYPT_MODE
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);

            // Update Additional Authentication Data
            cipher.updateAAD("zenon".getBytes());

            // Perform Encryption
            return cipher.doFinal(input);
        }

        public static byte[] decrypt(byte[] cipherData, byte[] key, byte[] IV) throws Exception {
            // Get Cipher Instance
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            // Create SecretKeySpec
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");

            // Create GCMParameterSpec
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);

            // Initialize Cipher for DECRYPT_MODE
            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);

            // Update Additional Authentication Data
            cipher.updateAAD("zenon".getBytes());

            // Perform Decryption
            return cipher.doFinal(cipherData);
        }
    }

    private static class CryptoData {
        private final Argon2Params argon2Params;
        private final byte[] cipherData;
        private final String cipherName;
        private final String kdf;
        private final byte[] nonce;

        public CryptoData(JCryptoData json) {
            this.argon2Params = json.argon2Params != null ? new Argon2Params(json.argon2Params) : null;
            this.cipherData = fromHexString(json.cipherData);
            this.cipherName = json.cipherName;
            this.kdf = json.kdf;
            this.nonce = fromHexString(json.nonce);
        }

        public CryptoData(Argon2Params argon2Params, byte[] cipherData, String cipherName, String kdf, byte[] nonce) {
            this.argon2Params = argon2Params;
            this.cipherData = cipherData;
            this.cipherName = cipherName;
            this.kdf = kdf;
            this.nonce = nonce;
        }

        public Argon2Params getArgon2Params() {
            return this.argon2Params;
        }

        public byte[] getCipherData() {
            return this.cipherData;
        }

        public String getCipherName() {
            return this.cipherName;
        }

        public String getKdf() {
            return this.kdf;
        }

        public byte[] getNonce() {
            return this.nonce;
        }

        public JCryptoData toJson() {
            JCryptoData json = new JCryptoData();
            json.argon2Params = this.argon2Params != null ? this.argon2Params.toJson() : null;
            json.cipherData = toHexString(this.cipherData);
            json.cipherName = this.cipherName;
            json.kdf = this.kdf;
            json.nonce = toHexString(this.nonce);
            return json;
        }
    }

    private static class Argon2Params {
        private final byte[] salt;

        public Argon2Params(JArgon2Params json) {
            this.salt = fromHexString(json.salt);
        }

        public Argon2Params(byte[] salt) {
            this.salt = salt;
        }

        public byte[] getSalt() {
            return this.salt;
        }

        public JArgon2Params toJson() {
            JArgon2Params json = new JArgon2Params();
            json.salt = toHexString(this.salt);
            return json;
        }
    }

    private final Address baseAddress;
    private final CryptoData crypto;
    private final int timestamp;
    private final int version;

    public KeyFile(JKeyFile json) {
        this.baseAddress = Address.parse(json.baseAddress);
        this.crypto = json.crypto != null ? new CryptoData(json.crypto) : null;
        this.timestamp = json.timestamp;
        this.version = json.version;
    }

    private KeyFile(Address baseAddress, CryptoData crypto, int timestamp, int version) {
        this.baseAddress = baseAddress;
        this.crypto = crypto;
        this.timestamp = timestamp;
        this.version = version;
    }

    public Address getBaseAddress() {
        return this.baseAddress;
    }

    private CryptoData getCrypto() {
        return this.crypto;
    }

    public int getTimestamp() {
        return this.timestamp;
    }

    public int getVersion() {
        return this.version;
    }

    public JKeyFile toJson() {
        JKeyFile json = new JKeyFile();
        json.baseAddress = this.baseAddress.toString();
        json.crypto = this.crypto != null ? this.crypto.toJson() : null;
        json.timestamp = this.timestamp;
        json.version = this.version;
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}
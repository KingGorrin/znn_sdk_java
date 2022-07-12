package network.zenon.wallet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import network.zenon.utils.JsonUtils;
import network.zenon.wallet.json.JKeyFile;

public class KeyStoreManager {
    private final Path walletPath;
    private KeyStore currentKeyStore;

    public KeyStoreManager(String walletPath) {
        this.walletPath = Paths.get(walletPath);
    }

    public Path getWalletPath() {
        return this.walletPath;
    }

    public KeyStore getKeyStoreInUse() {
        return this.currentKeyStore;
    }

    public void setKeyStoreInUse(KeyStore value) {
        this.currentKeyStore = value;
    }

    public Path saveKeyStore(KeyStore store, String password, String name) throws Exception {
        String fileName = name == null ? store.getKeyPair().getAddress().toString() : name;

        KeyFile encrypted = KeyFile.encrypt(store, password);
        Path filePath = this.walletPath.resolve(fileName);

        filePath = Files.write(filePath, encrypted.toString().getBytes());

        return filePath;
    }

    public String getMnemonicInUse() {
        if (this.currentKeyStore == null)
            throw new IllegalArgumentException("The keyStore in use is null");

        return this.currentKeyStore.getMnemonic();
    }

    public KeyStore readKeyStore(String password, String keyStorePath) throws Exception {
        return readKeyStore(password, Paths.get(keyStorePath));
    }

    public KeyStore readKeyStore(String password, Path keyStorePath) throws Exception {
        if (!Files.exists(keyStorePath))
            throw new InvalidKeyStorePathException(String.format("Given keyStore does not exist (%s)", keyStorePath));

        String content = new String(Files.readAllBytes(keyStorePath));
        return new KeyFile(JsonUtils.deserialize(content, JKeyFile.class)).decrypt(password);
    }

    public Path findKeyStore(String name) throws IOException {
        try (Stream<Path> pathStream = Files.find(this.walletPath, 0, (p, basicFileAttributes) -> {
            // if directory or no-read permission, ignore
            if (Files.isDirectory(p) || !Files.isReadable(p)) {
                return false;
            }
            return p.getFileName().toString().equals(name);
        })) {
            return pathStream.findFirst().orElse(null);
        }
    }

    public List<Path> ListAllKeyStores() throws IOException {
        List<Path> result;
        try (Stream<Path> pathStream = Files.find(this.walletPath, 0, (p, basicFileAttributes) -> {
            // if directory or no-read permission, ignore
            if (Files.isDirectory(p) || !Files.isReadable(p)) {
                return false;
            }
            return true;
        })) {
            result = pathStream.collect(Collectors.toList());
        }
        return result;
    }

    public Path createNew(String passphrase, String name) throws Exception {
        KeyStore store = KeyStore.newRandom();
        return saveKeyStore(store, passphrase, name);
    }

    public Path createFromMnemonic(String mnemonic, String passphrase, String name) throws Exception {
        KeyStore store = KeyStore.fromMnemonic(mnemonic);
        return saveKeyStore(store, passphrase, name);
    }
}

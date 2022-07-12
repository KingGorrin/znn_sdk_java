package network.zenon.wallet;

import java.nio.file.Path;
import java.nio.file.Paths;

import network.zenon.wallet.json.JFindResponse;

public class FindResponse {
    private final Path path;
    private final int index;
    private final KeyPair keyPair;

    public FindResponse(JFindResponse json) {
        this.path = Paths.get(json.keyStore);
        this.index = json.index;
        this.keyPair = null;
    }

    public FindResponse(Path path, int index, KeyPair keyPair) {
        this.path = path;
        this.index = index;
        this.keyPair = keyPair;
    }

    public Path getPath() {
        return this.path;
    }

    public int getIndex() {
        return this.index;
    }

    public KeyPair getKeyPair() {
        return this.keyPair;
    }

    public JFindResponse ToJson() {
        JFindResponse json = new JFindResponse();
        json.keyStore = this.path.toString();
        json.index = this.index;
        return json;
    }
}
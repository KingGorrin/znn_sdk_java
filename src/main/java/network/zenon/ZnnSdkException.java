package network.zenon;

public class ZnnSdkException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Zenon SDK Exception";

    public ZnnSdkException() {
        super(DEFAULT_MESSAGE);
    }

    public ZnnSdkException(String message) {
        super(DEFAULT_MESSAGE + ": " + message);
    }
}

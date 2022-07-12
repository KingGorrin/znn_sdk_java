package network.zenon;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZnnPaths {
    public static ZnnPaths DEFAULT = getDefault();

    public static ZnnPaths getDefault() {
        String osName = System.getProperty("os.name");
        osName = osName != null ? osName.toLowerCase() : "";

        Path main;

        if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            main = Paths.get(System.getenv("HOME"), "." + Constants.ZNN_ROOT_DIRECTORY);
        } else if (osName.contains("mac")) {
            main = Paths.get(System.getenv("HOME"), "Library", Constants.ZNN_ROOT_DIRECTORY);
        } else if (osName.contains("win")) {
            main = Paths.get(System.getenv("APPDATA"), Constants.ZNN_ROOT_DIRECTORY);
        } else {
            main = Paths.get(System.getenv("HOME"), Constants.ZNN_ROOT_DIRECTORY);
        }

        return new ZnnPaths(main.toString().toLowerCase(), main.resolve("wallet").toString().toLowerCase(),
                main.resolve("syrius").toString().toLowerCase());
    }

    private final String main;
    private final String wallet;
    private final String cache;

    public ZnnPaths(String main, String wallet, String cache) {
        this.main = main;
        this.wallet = wallet;
        this.cache = cache;
    }

    public String getMain() {
        return this.main;
    }

    public String getWallet() {
        return this.wallet;
    }

    public String getCache() {
        return this.cache;
    }
}

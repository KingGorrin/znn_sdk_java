package network.zenon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import uk.org.webcompere.systemstubs.properties.SystemProperties;

@ExtendWith(SystemStubsExtension.class)
public class ZnnPathsTest {
    @SystemStub
    private SystemProperties systemProperties;
    
    @SystemStub
    private EnvironmentVariables environmentVariables;
    
    @Nested
    public class Windows {
        @Test
        void assertDefaults() {
            // Setup
            Path root = Paths.get("c:", "users", "professorz", "appdata", "roaming");
            Path main = root.resolve("znn");
            Path wallet = main.resolve("wallet");
            Path cache = main.resolve("syrius");
            
            systemProperties.set("os.name", "Windows 10");
            environmentVariables.set("APPDATA", root.toString());
            
            // Execute
            ZnnPaths paths = ZnnPaths.getDefault();
            
            // Validate
            assertEquals(main.toString(), paths.getMain());
            assertEquals(wallet.toString(), paths.getWallet());
            assertEquals(cache.toString(), paths.getCache());
        }
    }
    
    @Nested
    public class Unix {
        @ParameterizedTest
        @CsvSource({ "'LINUX'", "'SINIX'", "'UNIX'", "'XENIX'", "'AIX-PS/2'" })
        void assertDefaults(String osName) {
            // Setup
            Path root = Paths.get("home", "professorz");
            Path main = root.resolve(".znn");
            Path wallet = main.resolve("wallet");
            Path cache = main.resolve("syrius");
            
            systemProperties.set("os.name", osName);
            environmentVariables.set("HOME", root.toString());
            
            // Execute
            ZnnPaths paths = ZnnPaths.getDefault();
            
            // Validate
            assertEquals(main.toString(), paths.getMain());
            assertEquals(wallet.toString(), paths.getWallet());
            assertEquals(cache.toString(), paths.getCache());
        }
    }
    
    @Nested
    public class Mac {
        @Test
        void assertDefaults() {
            // Setup
            Path root = Paths.get("users", "professorz");
            Path main = root.resolve(Paths.get("library", "znn"));
            Path wallet = main.resolve("wallet");
            Path cache = main.resolve("syrius");
            
            systemProperties.set("os.name", "mac");
            environmentVariables.set("HOME", root.toString());
            
            // Execute
            ZnnPaths paths = ZnnPaths.getDefault();
            
            // Validate
            assertEquals(main.toString(), paths.getMain());
            assertEquals(wallet.toString(), paths.getWallet());
            assertEquals(cache.toString(), paths.getCache());
        }
    }
    
    @Nested
    public class Unknown {
        @Test
        void assertDefaults() {
            // Setup
            Path root = Paths.get("");
            Path main = root.resolve("znn");
            Path wallet = main.resolve("wallet");
            Path cache = main.resolve("syrius");
            
            systemProperties.set("os.name", "");
            environmentVariables.set("HOME", "");
            
            // Execute
            ZnnPaths paths = ZnnPaths.getDefault();
            
            // Validate
            assertEquals(main.toString(), paths.getMain());
            assertEquals(wallet.toString(), paths.getWallet());
            assertEquals(cache.toString(), paths.getCache());
        }
    }
}
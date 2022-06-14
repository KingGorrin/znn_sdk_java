package network.zenon;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class TestHelper {
    public static String getRosourceText(String resourceName) throws IOException {
        ClassLoader classLoader = TestHelper.class.getClassLoader();
        InputStream in = classLoader.getResourceAsStream(resourceName);
        return StandardCharsets.UTF_8.decode(ByteBuffer.wrap(in.readAllBytes())).toString();
    }
}

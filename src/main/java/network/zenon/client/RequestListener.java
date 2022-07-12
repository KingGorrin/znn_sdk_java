package network.zenon.client;

import com.jsoniter.any.Any;

public interface RequestListener {
    void handleRequest(String method, Any params);
}

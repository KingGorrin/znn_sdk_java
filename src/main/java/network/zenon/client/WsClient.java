package network.zenon.client;

import java.io.IOException;

import org.kurento.jsonrpc.DefaultJsonRpcHandler;
import org.kurento.jsonrpc.Transaction;
import org.kurento.jsonrpc.client.JsonRpcClient;
import org.kurento.jsonrpc.client.JsonRpcClientNettyWebSocket;
import org.kurento.jsonrpc.message.Request;

import com.google.gson.JsonObject;
import com.jsoniter.JsonIterator;

public class WsClient extends DefaultJsonRpcHandler<JsonObject> implements IClient {
    private final JsonRpcClient client;

    public WsClient(String url) {
        this.client = new JsonRpcClientNettyWebSocket(url);

        client.setConnectionTimeout(5000);
        client.setServerRequestHandler(this);
    }

    public void connect() throws IOException {
        this.client.connect();
    }

    public void close() throws IOException {
        this.client.close();
    }

    @Override
    public <R> R sendRequest(String method, Object params, Class<R> classType) {
        try {
            Object element = this.client.sendRequest(method, params);
            return JsonIterator.parse(element.toString()).read(classType);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Object sendRequest(String method, Object params) {
        try {
            Object element = this.client.sendRequest(method, params);
            return JsonIterator.parse(element.toString()).readAny();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void sendNotification(String method, Object params) {
        try {
            this.client.sendNotification(method, params);
        } catch (IOException e) {
        }
    }

    @Override
    public void handleRequest(Transaction transaction, Request<JsonObject> request) throws Exception {
        String method = request.getMethod();

        if (method.equals("ledger.subscription")) {
            // TODO: add handlers
        }
    }
}

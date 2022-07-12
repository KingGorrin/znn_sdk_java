package network.zenon.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.kurento.jsonrpc.DefaultJsonRpcHandler;
import org.kurento.jsonrpc.Transaction;
import org.kurento.jsonrpc.client.JsonRpcClient;
import org.kurento.jsonrpc.client.JsonRpcClientNettyWebSocket;
import org.kurento.jsonrpc.message.Request;

import com.google.gson.JsonObject;

import network.zenon.utils.JsonUtils;

public class WsClient extends DefaultJsonRpcHandler<JsonObject> implements Client {
    private JsonRpcClient client;

    public WsClient() {
    }

    public void connect(String url) throws IOException {
        if (this.client == null) {
            this.client = new JsonRpcClientNettyWebSocket(url);
            this.client.setConnectionTimeout(5000);
            this.client.setServerRequestHandler(this);
        }

        this.client.connect();
    }

    public void close() throws IOException {
        if (this.client != null)
            this.client.close();
        this.client = null;
    }

    @Override
    public <R> R sendRequest(String method, Object params, Class<R> classType) {
        try {
            Object element = this.client.sendRequest(method, params);
            return JsonUtils.deserialize(element.toString(), classType);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Object sendRequest(String method, Object params) {
        try {
            Object element = this.client.sendRequest(method, params);
            return JsonUtils.deserializeAny(element.toString());
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
    public void handleRequest(Transaction transaction, Request<JsonObject> request) {
        String method = request.getMethod();
        String params = request.getParams().toString();

        for (RequestListener rl : listeners) {
            rl.handleRequest(method, JsonUtils.deserializeAny(params));
        }
    }

    private List<RequestListener> listeners = new ArrayList<>();

    public void addListener(RequestListener toAdd) {
        listeners.add(toAdd);
    }

    public void removeListener(RequestListener toRemove) {
        listeners.remove(toRemove);
    }
}
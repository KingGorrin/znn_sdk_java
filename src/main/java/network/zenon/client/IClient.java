package network.zenon.client;

public interface IClient
{
	<R> R sendRequest(String method, Object params, Class<R> resultClass);
	
	Object sendRequest(String method, Object params);
	
	void sendNotification(String method, Object params);
}
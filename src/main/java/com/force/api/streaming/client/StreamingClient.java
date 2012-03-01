package com.force.api.streaming.client;

import java.util.List;

/**
 * The minimum functional set for a client that connect to Bayeux APIs. 
 * 
 * @author gwester
 */
public interface StreamingClient {
	public boolean handshake() throws Exception;
	
	public boolean subscribe(String topicName) throws Exception;
	
	public List<String> connect(String clientId) throws Exception;
	
	public boolean unsubscribe(String topicName) throws Exception;
}

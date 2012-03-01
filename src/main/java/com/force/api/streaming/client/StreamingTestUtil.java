package com.force.api.streaming.client;

import java.io.IOException;
import java.util.List;

/**
 * This is a utility that wraps the cometd client written at Salesforce.
 * 
 * @author gwester
 */
public class StreamingTestUtil {
	
    //private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(StreamingTestUtil.class.getName());
    
    //streaming wide defaults
    public static final int CONN_TIMEOUT = 30 * 1000;

    //clients
    private final StreamingTestClient streamingClient;
    
    //sessions
    private final BayeuxConnectRequest connectionRequest;
    
    private String clientId;
    
    /**
     * Types of DML operations that can create a streaming notification
     */
    public static enum PushEventType {
        CREATE("c"),
        UPDATE("u");
        
        private String name;
        
        PushEventType(String name) {
            this.name = name;
        }
        
        public String getApiValue() {
            return name;
        }
    }

    /**
     * Constructor.
     * This will do a handshake with server and establish a clientId.
     * 
     * @param protocolAndHostname For example: https://na1-blitz01.soma.salesforce.com 
     * @param sid The Salesforce Session ID.
     * @throws Exception
     */
    public StreamingTestUtil(String protocolAndHostname, String sid) throws Exception {
        streamingClient = new StreamingTestClient(protocolAndHostname, sid);
        
        //start a session
        connectionRequest = handshake();
    }

    /**
     * This is a convenience method for doing a handshake and connect in one. It establishes a new clientId with the
     * cometd server for you.
     * 
     * @throws Exception 
     */
    private BayeuxConnectRequest handshake() throws Exception {
        BayeuxHandshakeResponse handshakeReply = streamingClient.sendHandshakeRequest();

        clientId = handshakeReply.getClientId();
        return new BayeuxConnectRequest(clientId);
    }
    
    /**
     * This method does a subscribes to a channel.
     * 
     * @param topicNames varArg for subscribing to topics, then long polling for streaming events. 
     * @throws StreamingApiException 
     * @throws IOException 
     * @throws Exception 
     */
    public void subscribe(String ... topicNames) throws Exception {
        for(String topic : topicNames) {
            BayeuxSubscribeRequest subscription = new BayeuxSubscribeRequest(clientId, "/topic/" + topic);
            streamingClient.sendSubscribeRequest(subscription);
        }
    }
    
    public void unsubscribe(String ... topicNames) throws Exception {
        for(String topic : topicNames) {
            BayeuxUnsubscribeRequest unsubscription = new BayeuxUnsubscribeRequest(clientId, "/topic/" + topic);
            streamingClient.sendUnsubscribeRequest(unsubscription);
        }
    }
    
    /**
     * This method initiates long polling connections with the server and returns event messages,
     * also known as notifications
     * 
     * @return The aggregate of all events received by all clients.
     * @throws StreamingApiException, Exception 
     */
    public List<BayeuxEventResponse> longPoll() throws Exception {
        return streamingClient.longPoll(connectionRequest);
    }
 
}

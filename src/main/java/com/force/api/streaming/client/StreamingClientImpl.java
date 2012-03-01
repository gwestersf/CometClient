package com.force.api.streaming.client;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;

import com.google.common.collect.Lists;

/**
 * This is a stateful streaming client that keeps track of recently collected events.
 * If the cometd session times out your code will have to reconnect.
 *
 * @author gwester
 */
public class StreamingClientImpl extends StatelessStreamingClient implements StreamingClient {

    private static final Logger logger = Logger.getLogger(StreamingClientImpl.class.getName());
    
    private BayeuxConnectRequest lastConnectRequest;
    private final List<BayeuxEventResponse> events;

    public StreamingClientImpl(String protocolAndHostname, String sid) throws Exception {
         super(protocolAndHostname, sid);
         events = Lists.<BayeuxEventResponse>newArrayList();
         sendHandshakeRequest();
    }

    public BayeuxConnectRequest getLastConnectRequest() {
         return lastConnectRequest;
    }
    
    public String getClientId() {
         return lastConnectRequest.getClientId();
    }

    public List<BayeuxEventResponse> getCumulativeEvents() {
         return events;
    }
    
    public void clearEvents() {
         events.clear();
    }
    
    public List<String> connect(String clientId) throws IOException, StreamingApiException, JSONException {
    	 lastConnectRequest = new BayeuxConnectRequest(clientId);
    	 List<String> rawEvents = super.sendRequest();
       	 List<BayeuxEventResponse> newEvents = StatelessStreamingClient.castRawMessagesToBayeuxEvents(rawEvents); 
         events.addAll(newEvents);
         return rawEvents;
    }

	public boolean subscribe(String topicName) throws Exception {
         BayeuxSubscribeResponse response = super.sendSubscribeRequest(new BayeuxSubscribeRequest(getClientId(), topicName));
		 if(response.getSuccessful()) {
			 return true;
		 }
		 logger.log(Level.SEVERE, response.getError());
		 return false;
	}

	public boolean unsubscribe(String topicName) throws Exception {
         BayeuxUnsubscribeResponse response = super.sendUnsubscribeRequest(new BayeuxUnsubscribeRequest(getClientId(), topicName));
		 if(response.getSuccessful()) {
			 return true;
		 }
		 logger.log(Level.SEVERE, response.getError());
		 return false;
	}
    
    
    public boolean handshake() throws Exception {
         BayeuxHandshakeResponse response = super.sendHandshakeRequest();
		 if(response.getSuccessful()) {
		     lastConnectRequest = new BayeuxConnectRequest(response.getClientId());
			 return true;
		 }
		 logger.log(Level.SEVERE, response.getError());
		 return false;
    }
    
    @Override
    public BayeuxConnectResponse sendConnectRequest(BayeuxConnectRequest connect) throws Exception {
        lastConnectRequest = connect;
        return super.sendConnectRequest(connect);
    }

}

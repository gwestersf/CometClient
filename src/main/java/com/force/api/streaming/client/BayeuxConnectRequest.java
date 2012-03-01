package com.force.api.streaming.client;


/**
 * A Bayeux Connect message sent from the client to the server.
 * 
 * @author gwester
 */
public class BayeuxConnectRequest extends StreamingApiRequest {
    // REQUIRED request fields
    private final String channel = "/meta/connect";
    private final String clientId;
    private final String connectionType = "long-polling";
    private BayeuxAdviceRequest advice;

    // OPTIONAL response fields
    private String id;
    private String ext;

    /**
     * String parameter constructor for spoofing.
     * @param clientId
     * @param connectionType
     */
    public BayeuxConnectRequest(String clientId, BayeuxAdviceRequest advice) {
        this.clientId = clientId;
        this.advice = advice;
    }

    /**
     * Minimal constructor needs the client ID from the handshake response. Defaults to long polling.
     * @param clientId
     */
    public BayeuxConnectRequest(String clientId) {
        this(clientId, new BayeuxAdviceRequest());
    }

    /**
     * Defaults to long polling.
     * @param lastResponse
     */
    public BayeuxConnectRequest(BayeuxHandshakeResponse lastResponse) {
        this(lastResponse.getClientId(), new BayeuxAdviceRequest());
    }
    
    /**
     * Sets the advice to null.  Invoke when you no longer want to pass timeout and reconnect interval advice to cometd server.
     */
    public void deleteAdvice() {
        advice = null;
    }

    @Override
    public String getChannel() {
        return channel;
    }

    public String getClientId() {
        return clientId;
    }

    public String getConnectionType() {
        return connectionType;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setExt(String ext) {
        this.ext = ext;
    }
    
    String getExt() {
        return ext;
    }
    
    String getId() {
        return id;
    }

    public Integer getSocketTimeoutAdvice() {
        return advice.getTimeout();
    }
}

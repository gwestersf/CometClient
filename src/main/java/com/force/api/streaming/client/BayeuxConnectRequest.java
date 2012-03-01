package test.util.api.streaming;

import test.util.api.streaming.BayeuxHandshakeRequest.StreamingConnectionType;

/**
 * A Bayeux Connect message sent from the client to the server.
 * 
 * @author gwester
 * @since 172
 */
public class BayeuxConnectRequest extends StreamingApiRequest {
    // REQUIRED request fields
    private final String channel = "/meta/connect";
    private final String clientId;
    private final String connectionType;
    private BayeuxAdviceRequest advice;

    // OPTIONAL response fields
    private String id;
    private String ext;

    /**
     * String parameter constructor for spoofing.
     * @param clientId
     * @param connectionType
     */
    public BayeuxConnectRequest(String clientId, String connectionType, BayeuxAdviceRequest advice) {
        this.clientId = clientId;
        this.connectionType = connectionType;
        this.advice = advice;
    }

    /**
     * Constructor with strong typing.
     * @param clientId
     * @param connectionType
     */
    public BayeuxConnectRequest(String clientId, StreamingConnectionType connectionType) {
        this(clientId, connectionType.toString(), null);
    }

    /**
     * Minimal constructor needs the client ID from the handshake response. Defaults to long polling.
     * @param clientId
     */
    public BayeuxConnectRequest(String clientId) {
        this(clientId, StreamingConnectionType.LONG_POLLING.toString(), new BayeuxAdviceRequest());
    }

    /**
     * Defaults to long polling.
     * @param lastResponse
     */
    public BayeuxConnectRequest(BayeuxHandshakeResponse lastResponse) {
        this(lastResponse.getClientId(), StreamingConnectionType.LONG_POLLING.toString(), new BayeuxAdviceRequest());
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

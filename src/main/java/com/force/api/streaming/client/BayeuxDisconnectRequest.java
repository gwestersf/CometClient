package com.force.api.streaming.client;

/**
 * @author gwester
 */
public class BayeuxDisconnectRequest extends StreamingApiRequest {
    // REQUIRED
    private final String channel = "/meta/disconnect";
    private final String clientId;

    // OPTIONAL response fields
    private String id;
    private String ext;
    private String error;

    /**
     * Required fields constructor.
     * 
     * @param channel
     * @param clientId
     */
    public BayeuxDisconnectRequest(String clientId) {
        this(clientId, null, null, null);
    }

    /**
     * All fields constructor.
     * 
     * @param channel
     * @param clientId
     * @param id
     * @param ext
     * @param error
     */
    public BayeuxDisconnectRequest(String clientId, String id, String ext, String error) {
        super();
        this.clientId = clientId;
        this.id = id;
        this.ext = ext;
        this.error = error;
    }

    @Override
    public String getChannel() {
        return channel;
    }

    public String getClientId() {
        return clientId;
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

    public String getError() {
        return error;
    }

}

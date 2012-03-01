package com.force.api.streaming.client;

/**
 * @author gwester
 */
public class BayeuxDisconnectResponse extends StreamingApiResponse {
    // REQUIRED
    private String channel;
    private String clientId;
    private Boolean successful;

    // OPTIONAL response fields
    private String id;
    private String ext;
    private String error;

    @Override
    public String getChannel() {
        return channel;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public Boolean getSuccessful() {
        return successful;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getExt() {
        return ext;
    }
    
    @Override
    public String getError() {
        return error;
    }
    
}

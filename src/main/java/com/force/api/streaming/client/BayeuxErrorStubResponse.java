package com.force.api.streaming.client;

/**
 * Any Streaming API JSON response can be deserialized into this message, to find errors.
 * 
 * @author gwester
 */
public class BayeuxErrorStubResponse {
    private Boolean successful;
    private String error;
    
    public Boolean hasError() {
        return !successful;
    }
    
    public String getError() {
        if(successful) {
            return null;
        } else {
            return this.error;
        }
    }
}

package com.force.api.streaming.client;

/**
 * 
 * This class is for sending the client's socket timeout up to the server.
 *
 * @author gwester
 */
public class BayeuxAdviceRequest {
    private final int timeout;
    private final int interval;
    
    public BayeuxAdviceRequest() {
        this(Math.max(5000, 15 * 1000), 0);
    }
    
    public BayeuxAdviceRequest(Integer timeoutInMilliseconds) {
        this(timeoutInMilliseconds, 0);
    }
    
    public BayeuxAdviceRequest(Integer timeoutInMilliseconds, Integer interval) {
        timeout = timeoutInMilliseconds;
        this.interval = interval;
    }
    
    public int getTimeout() {
        return timeout;
    }
    
    public int getInterval() {
        return interval;
    }
}

package com.force.api.streaming.client;

/**
 * These snippets can appear in any response from the server.
 * 
 * @author gwester
 */
public class BayeuxAdviceResponse {
    private String reconnect;
    private String interval;

    public String getReconnect() {
        return reconnect;
    }

    public String getInterval() {
        return interval;
    }
}

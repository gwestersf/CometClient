package com.force.api.streaming.client;

/**
 * This message allows you to send any JSON message to cometd that you want.
 * For use with the Streaming API client.
 * 
 * @author gwester
 */
public class SpoofableStreamingMessage extends StreamingApiRequest {
    private final String json;

    public SpoofableStreamingMessage(String json) {
        this.json = json;
    }

    @Override
    public String toJson() {
        return json;
    }

    @Override
    public String getChannel() {
        return null;
    }

    @Override
    public void setExt(String ext) { }

    @Override
    public void setId(String id) { }
}

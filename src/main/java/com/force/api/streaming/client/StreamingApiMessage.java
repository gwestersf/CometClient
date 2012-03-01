package com.force.api.streaming.client;

import com.google.gson.Gson;

/**
 * Parent class for all Streaming API messages - request OR response.
 * 
 * @author gwester
 */
public abstract class StreamingApiMessage {

    protected static final Gson gson = new Gson();

    /**
     * @return A JSON parser called Google GSON.
     */
    public static Gson jsonParser() {
        return gson;
    }
}

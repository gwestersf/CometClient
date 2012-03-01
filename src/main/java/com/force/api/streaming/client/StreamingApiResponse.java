package test.util.api.streaming;

import java.util.regex.Pattern;

/**
 * Base class for all Streaming API JSON resposnes.
 * 
 * @author gwester
 * @since 172
 */
public abstract class StreamingApiResponse extends StreamingApiMessage {
    
    abstract public String getId();
    
    abstract public String getChannel();

    abstract public String getClientId();

    abstract public Boolean getSuccessful();

    abstract public String getExt();
    
    abstract public String getError();
    
    /**
     * This gives me a way to figure out what raw message I'm dealing with before I deserialize it.
     * @author gwester
     * @since 172
     */
    public static enum ChannelType {
        CONNECT(BayeuxConnectResponse.class, Pattern.compile("/meta/connect")),
        DISCONNECT(BayeuxConnectResponse.class, Pattern.compile("/meta/disconnect")),
        HANDSHAKE(BayeuxHandshakeResponse.class, Pattern.compile("/meta/handshake")),
        SUBSCRIBE(BayeuxSubscribeResponse.class, Pattern.compile("/meta/subscribe")),
        UNSUBSCRIBE(BayeuxUnsubscribeResponse.class, Pattern.compile("/meta/unsubscribe"));
        
        private Class<? extends StreamingApiResponse> clazz;
        private Pattern pattern;
        
        ChannelType(Class<? extends StreamingApiResponse> clazz, Pattern pattern) {
            this.clazz = clazz;
            this.pattern = pattern;
        }
        
        public Class<? extends StreamingApiResponse> getResponseType() {
            return clazz;
        }
        
        public Pattern getMetaPattern() {
            return pattern;
        }
    }
    

    /**
     * Get exactly the Streaming API Message subclass you want from JSON.
     * @param <T>
     * @param clazz
     * @param json
     * @return
     */
    public static <T extends StreamingApiMessage> T fromJson(Class<T> clazz, String json) {
        return gson.fromJson(json, clazz);
    }

    /**
     * This method dynamically figures out what type of Streaming API Message you've got.
     * @param json
     * @return
     */
    public static StreamingApiResponse fromJson(String json) {
        for(ChannelType channel : ChannelType.values()) {
            if(channel.getMetaPattern().matcher(json).find()) {
                return gson.fromJson(json, channel.getResponseType());
            }
        }
        throw new IllegalStateException("JSON does not have a channel corresponding to a known Streaming API JSON response type.");
    }
}

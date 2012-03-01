package test.util.api.streaming;

/**
 * These snippets can appear in any response from the server.
 * 
 * @author gwester
 * @since 172
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

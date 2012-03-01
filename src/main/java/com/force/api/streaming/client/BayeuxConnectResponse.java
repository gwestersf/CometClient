package test.util.api.streaming;

/**
 * @author gwester
 * @since 172
 */
public class BayeuxConnectResponse extends StreamingApiResponse {
    // REQUIRED
    private String channel;
    private String clientId;
    private Boolean successful;

    // OPTIONAL response fields
    private String id;
    private String ext;
    private String error;
    private BayeuxAdviceResponse advice;
    private String timestamp;

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

    public BayeuxAdviceResponse getAdvice() {
        return advice;
    }

    public String getTimestamp() {
        return timestamp;
    }

}

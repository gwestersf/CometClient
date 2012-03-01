package test.util.api.streaming;


/**
 * @author gwester
 * @170
 */
public class BayeuxSubscribeResponse extends StreamingApiResponse {
    // REQUIRED
    private String channel;
    private String clientId;
    private String subscription;
    private Boolean successful;

    // OPTIONAL
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

    public String getSubscription() {
        return subscription;
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
package test.util.api.streaming;

/**
 * Bayeux protocol subscribe object, for requests on /meta/subscribe
 * 
 * @author gwester
 * @since 172
 */
public class BayeuxSubscribeRequest extends StreamingApiRequest {
    // REQUIRED request fields
    private final String channel = "/meta/subscribe";
    private final String clientId;
    private final String subscription;

    // OPTIONAL request fields
    private String id;
    private String ext;

    /**
     * Required fields consturctor.
     * 
     * @param clientId
     * @param subscription
     */
    public BayeuxSubscribeRequest(String clientId, String subscription) {
        this(clientId, subscription, null, null);
    }

    /**
     * All fields constructor.
     * 
     * @param clientId
     * @param subscription
     * @param id
     * @param ext
     */
    public BayeuxSubscribeRequest(String clientId, String subscription, String id, String ext) {
        super();
        this.clientId = clientId;
        this.subscription = subscription;
        this.id = id;
        this.ext = ext;
    }

    @Override
    public String getChannel() {
        return channel;
    }

    public String getClientId() {
        return clientId;
    }

    public String getSubscription() {
        return subscription;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setExt(String ext) {
        this.ext = ext;
    }
    
    String getExt() {
        return ext;
    }
    
    String getId() {
        return id;
    }

}

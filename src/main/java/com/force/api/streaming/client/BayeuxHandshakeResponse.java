package test.util.api.streaming;

import java.util.Set;

/**
 * @author gwester
 * @since 172
 */
public class BayeuxHandshakeResponse extends StreamingApiResponse {
    // REQUIRED response fields
    private String clientId; // this is the most important one
    private String channel;
    private String version;
    private Set<String> supportedConnectionTypes;
    private Boolean successful;

    // OPTIONAL response fields
    private String id;
    private String ext;
    private String minimumVersion;
    private BayeuxAdviceResponse advice;
    private Boolean authSuccessful;
    private String error; // only non-null if successful==false

    @Override
    public String getClientId() {
        return clientId;
    }

    public String getVersion() {
        return version;
    }
    
    public Set<String> getSupportedConnectionTypes() {
        return supportedConnectionTypes;
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

    public String getMinimumVersion() {
        return minimumVersion;
    }

    public BayeuxAdviceResponse getAdvice() {
        return advice;
    }

    public Boolean getAuthSuccessful() {
        return authSuccessful;
    }
    
    @Override
    public String getError() {
        return error;
    }

    @Override
    public String getChannel() {
        return channel;
    }
    
}

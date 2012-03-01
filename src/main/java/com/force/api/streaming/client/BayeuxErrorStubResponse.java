package test.util.api.streaming;

/**
 * Any Streaming API JSON response can be deserialized into this message, to find errors.
 * 
 * @author gwester
 * @since 172
 */
public class BayeuxErrorStubResponse {
    private Boolean successful;
    private String error;
    
    public Boolean hasError() {
        return !successful;
    }
    
    public String getError() {
        if(successful) {
            return null;
        } else {
            return this.error;
        }
    }
}

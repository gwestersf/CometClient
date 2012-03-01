package test.util.api.streaming;

import java.util.HashSet;
import java.util.Set;

/**
 * Bayeux protocol handshake object, for requests on /meta/handshake
 * 
 * @author gwester
 * @since 172
 */
public class BayeuxHandshakeRequest extends StreamingApiRequest {
    // REQUIRED request fields
    private final String channel = "/meta/handshake";
    private final String version;
    private final Set<String> supportedConnectionTypes;

    // OPTIONAL request fields
    private String id;
    private String ext;
    private String minimumVersion;

    /**
     * The versions of the cometd protocol supported by Salesforce.
     * 
     * @author gwester
     * @since 172
     */
    public static enum StreamingVersionType {
        VERSION_1("1.0");

        private String version;

        StreamingVersionType(String version) {
            this.version = version;
        }

        /**
         * @return The version number. For example, "21.0"
         */
        @Override
        public String toString() {
            return version;
        }
    }

    /**
     * The streaming connection types supported by Bayeux Protocol. Salesforce only supports LONG_POLLING.
     * 
     * @author gwester
     * @since 172
     */
    public static enum StreamingConnectionType {
        LONG_POLLING("long-polling"),
        CALLBACK_POLLING("callback-polling"),
        IFRAME("iframe");

        private String value;

        StreamingConnectionType(String value) {
            this.value = value;
        }

        /**
         * The case-appropriate, formatted string for this enum value.
         */
        @Override
        public String toString() {
            return value;
        }
    }

    /**
     * Constructor
     */
    public BayeuxHandshakeRequest() {
        version = StreamingVersionType.VERSION_1.toString();
        supportedConnectionTypes = new HashSet<String>();
        supportedConnectionTypes.add(StreamingConnectionType.LONG_POLLING.toString());
    }

    /**
     * Variable argument length constructor with strong typing.
     * 
     * @param version
     * @param supportedConnectionTypes
     */
    public BayeuxHandshakeRequest(StreamingVersionType version, StreamingConnectionType... supportedConnectionTypes) {
        this.version = version.toString();

        Set<String> types = new HashSet<String>();
        for (StreamingConnectionType type : supportedConnectionTypes) {
            types.add(type.toString());
        }

        this.supportedConnectionTypes = types;
    }

    /**
     * String constructor for spoofing values.
     * 
     * @param version
     * @param supportedConnectionTypes
     */
    public BayeuxHandshakeRequest(String version, Set<String> supportedConnectionTypes) {
        this.version = version;
        this.supportedConnectionTypes = supportedConnectionTypes;
    }

    @Override
    public String getChannel() {
        return channel;
    }

    public String getVersion() {
        return version;
    }

    public Set<String> getSupportedConnectionTypes() {
        return supportedConnectionTypes;
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

    public String getMinimumVersion() {
        return minimumVersion;
    }
}

package com.force.api.streaming.client;


/**
 * Bayeux protocol handshake object, for requests on /meta/handshake
 * 
 * @author gwester
 */
public class BayeuxHandshakeRequest extends StreamingApiRequest {
    // REQUIRED request fields
    private final String channel = "/meta/handshake";
    private final String version = "1.0";	//this is the bayeux version, not the Salesforce API version
    private final String supportedConnectionType = "long-polling";

    // OPTIONAL request fields
    private String id;
    private String ext;
    private String minimumVersion;


    /**
     * Constructor
     */
    public BayeuxHandshakeRequest() {
    	
    }

    @Override
    public String getChannel() {
        return channel;
    }

    public String getVersion() {
        return version;
    }

    public String getSupportedConnectionType() {
        return supportedConnectionType;
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

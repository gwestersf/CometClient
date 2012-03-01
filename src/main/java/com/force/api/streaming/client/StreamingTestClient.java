package test.util.api.streaming;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderElement;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import test.util.api.streaming.BayeuxHandshakeRequest.StreamingConnectionType;
import test.util.api.streaming.BayeuxHandshakeRequest.StreamingVersionType;

/**
 * 
 * This is a CometD client written at Salesforce for testing every request and response per the protocol.
 *
 * @author gwester
 * @since 172
 */
public class StreamingTestClient {
    private static final Logger logger = Logger.getLogger(StreamingTestClient.class.getName());

    protected final HttpClient httpClient = new HttpClient();
    protected String cookieValues;
    protected String sid;
    
    private static final String CONTENT_TYPE = "application/json";
    private final String BASE_URL;

    /**
     * Use your Salesforce session ID to start talking to the /cometd endpoint.
     * Uses a default connection timeout appropriate for prod test.
     * 
     * @param protocolAndHostname For example: https://na1-blitz01.soma.salesforce.com
     * @param sid The Salesforce Session ID.
     * @throws Exception
     */
    public StreamingTestClient(String protocolAndHostname, String sid) throws Exception {
        if(!protocolAndHostname.contains("http")) {
            throw new IllegalStateException("Must include protocol");
        }
        
        if(protocolAndHostname.lastIndexOf("/") == protocolAndHostname.length()) {
            BASE_URL = protocolAndHostname;
        } else {
            BASE_URL = protocolAndHostname + "/";
        }

        // set the cookie array; we will append any Set-Cookie headers from the server to this String
        cookieValues = new String();
        this.sid = sid;

        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(StreamingTestUtil.CONN_TIMEOUT);
        httpClient.getParams().setSoTimeout(30 * 1000);
     }

    /**
     * A default handshake request.
     * This resets the state of your client; you will get a new client Id from the server.
     * This clientId will not be subscribed to any channels.
     * This method deletes all local storage of events.
     * 
     * @return
     * @throws IOException
     * @throws StreamingApiException 
     * @throws HttpException
     */
    public BayeuxHandshakeResponse sendHandshakeRequest() throws Exception {
        // set up a default handshake
        BayeuxHandshakeRequest handy = new BayeuxHandshakeRequest(StreamingVersionType.VERSION_1,
                StreamingConnectionType.LONG_POLLING);
        return sendHandshakeRequest(handy);
    }

    /**
     * This is the first call made to the Streaming API by any client.
     * 
     * @param handshake
     * @return
     * @throws IOException
     * @throws StreamingApiException 
     * @throws HttpException
     */
    public BayeuxHandshakeResponse sendHandshakeRequest(BayeuxHandshakeRequest handshake) throws Exception {
        String jsonResponse = sendRequest(handshake).get(0);
        return StreamingApiMessage.jsonParser().fromJson(jsonResponse, BayeuxHandshakeResponse.class);
    }

    /**
     * This is the second call made to the streaming API by any client. This jsonResponse may contain BOTH a connect
     * response AND event responses if this client ID is already subscribed to a channel.
     * 
     * @param connect
     * @return
     * @throws IOException
     * @throws StreamingApiException 
     * @throws HttpException
     */
    public BayeuxConnectResponse sendConnectRequest(BayeuxConnectRequest connect) throws Exception {
        String jsonResponse = sendRequest(connect).get(0);
        
        // delete the client socket timeout advice for the server; the server will remember what we sent
        connect.deleteAdvice();
        
        return StreamingApiMessage.jsonParser().fromJson(jsonResponse, BayeuxConnectResponse.class);
    }

    /**
     * This is the third call made to the streaming API by the client. This jsonResponse may contain BOTH a subscribed
     * response AND event responses.
     * 
     * @param subscription
     * @return
     * @throws IOException
     * @throws StreamingApiException 
     * @throws HttpException
     */
    public BayeuxSubscribeResponse sendSubscribeRequest(BayeuxSubscribeRequest subscription) throws Exception {
        String jsonResponse = sendRequest(subscription).get(0);
        return StreamingApiMessage.jsonParser().fromJson(jsonResponse, BayeuxSubscribeResponse.class);
    }

    /**
     * This is a Bayeux unsubscribe request / response method.
     * 
     * @param unsubscription
     * @return
     * @throws HttpException
     * @throws IOException
     * @throws StreamingApiException 
     */
    public BayeuxUnsubscribeResponse sendUnsubscribeRequest(BayeuxUnsubscribeRequest unsubscription) throws Exception {
        String jsonResponse = sendRequest(unsubscription).get(0);
        return StreamingApiMessage.jsonParser().fromJson(jsonResponse, BayeuxUnsubscribeResponse.class);
    }

    /**
     * This is a Bayeux Disconnect request / response method.
     * 
     * @param unsubscription
     * @return
     * @throws Exception 
     * @throws HttpException
     * @throws StreamingApiException 
     */
    public BayeuxDisconnectResponse sendDisconnectRequest(BayeuxDisconnectRequest disconnect) throws Exception {
        String jsonResponse = sendRequest(disconnect).get(0);
        return StreamingApiMessage.jsonParser().fromJson(jsonResponse, BayeuxDisconnectResponse.class);
    }
    
    public List<BayeuxEventResponse> longPoll(BayeuxConnectRequest connectionRequest) throws Exception {
        List<String> possibleEvents = sendRequest(connectionRequest);

        //parse the connect response off the end
        String connectResponse = possibleEvents.remove(possibleEvents.size() - 1);
        logger.info(connectResponse);
        BayeuxErrorStubResponse successMsg = StreamingApiMessage.jsonParser().fromJson(connectResponse, BayeuxErrorStubResponse.class);
        if(successMsg.hasError()) {
        	throw new Exception(successMsg.getError());
        }
        
        //if there's no events, just return an empty list
        if(possibleEvents.size() == 0) return new ArrayList<BayeuxEventResponse>();
        
        logger.info("Long polling response: " + possibleEvents.toString());
        
        //deal with the events
        List<BayeuxEventResponse> events = new ArrayList<BayeuxEventResponse>();
        for(String possibleEvent : possibleEvents) {
            events.add(StreamingApiMessage.jsonParser().fromJson(possibleEvent, BayeuxEventResponse.class));
        }
        
        return events;
    }   

    /**
     * This method sends an HTTP POST with any JSON content to the server that you give it. It sends up a single header
     * for all cookies (which should be semicolon separated). It specifies the content-type as application/json.
     * 
     * @param contentType
     * @param request
     *            A bayeux message in JSON, such as a Handshake, Connect, Subscribe, Unsubscribe, or Disconnect
     * @return The JSON response from the server, or a null string if an HTTP error occurs. This is a JSON array!
     *         [{"foo":"bar"}{"foo":"bar2"}]
     * @throws Exception 
     * @throws AuthenticationInvalidException 
     * @throws HttpException
     */
    public List<String> sendRequest(StreamingApiRequest ... requests) throws Exception {
        String json = createJsonArray(requests);

        // set up an HTTP POST
        PostMethod method = new PostMethod(BASE_URL);
        method.setRequestHeader("Cookie", cookieValues);
        method.setRequestHeader("Content-Type", CONTENT_TYPE);
        method.setRequestHeader("Authorization", "OAuth " + sid);
        
        @SuppressWarnings("deprecation")
        StringRequestEntity sreq = new StringRequestEntity(json);
        method.setRequestEntity(sreq);     //JSON in the body

        // execute the method on an HTTP client
        logger.info("POST " + BASE_URL + " Cookie:" + cookieValues + ", Body:" + json);
        int code = -1;
        try {
            code = httpClient.executeMethod(method);
        } catch (SocketException e) {
            if (e.getMessage().contains("Connection reset")) {
                // try one more time
                code = httpClient.executeMethod(method);
            }
        }
        
        // update cookies, get response body
        updateCookies(method.getResponseHeaders("Set-cookie"));
        String responseBody = IOUtils.toString(method.getResponseBodyAsStream());
        logger.info("HTTP " + code + " " + responseBody);

        // null response handling
        if (responseBody == null || responseBody.isEmpty()) {
            throw new Exception("The server response was null, with status code HTTP " + code);
        }
        
        // HTTP error handling
        if(code != 200) {
            throw new Exception(method.getStatusText());
        }
        List<String> bayeuxMessages = parseJsonArray(responseBody);

        // Let's return our collection of 1 or more JSON messages.
        return bayeuxMessages;
    }
    
    /**
     * If there is more than one request, this function will wrap them in brackets []
     * and comma delimit the messages.
     * @param requests
     * @return
     * @throws JSONException 
     */
    private String createJsonArray(StreamingApiRequest ... requests) throws JSONException {
    	JSONArray array = new JSONArray();
    	for(StreamingApiRequest request : requests) {
    		JSONObject json = new JSONObject(request.toJson());
    		array.put(json);
    	}
    	return array.toString();
    }
    
    /**
     * This pulls a JSON array apart into a list of Strings.
     * @param oneOrMoreJsonMessages
     * @return
     * @throws JSONException 
     */
    private List<String> parseJsonArray(String oneOrMoreJsonMessages) throws JSONException {
        JSONArray array = new JSONArray(oneOrMoreJsonMessages);
        List<String> jsonMessages = new ArrayList<String>();
        for(int i=0; i < array.length(); i++) {
        	String json = array.getJSONObject(i).toString();
        	jsonMessages.add(json);
        }
        return jsonMessages;
    }
    
    /**
     * Reads one or more headers and updates our cookie store.
     * @param headers These should be headers that were named "Set-Cookie"
     */
    private void updateCookies(Header[] headers) {
        for (Header header : headers) {
        	logger.info(header.toString());
            for (HeaderElement element : header.getElements()) {
                String name = element.getName();
                String value = element.getValue();
                if (!cookieValues.contains(name)) {
                    if (value != null) {
                        String cookie = "; " + name + "=" + value;
                        cookieValues += cookie;
                    }
                }
            }
        }
    }
}

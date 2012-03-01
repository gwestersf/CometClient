package com.force.api.streaming.client;

import java.util.Map;

/**
 * {"channel":"/topic/prodtest","data":{"event":{"type":"created","createdDate":"2011-08-10T20:29:54.000+0000"},"sobject":{"Name":"0","Id":"001D000000J51BKIAZ"}}}
 * 
 * @author gwester
 */
public class BayeuxEventResponse {
    // REQUIRED
    private String channel;
    private BayeuxEventResponsePayload data;
     
    public String getChannel() {
        return channel;
    }

    public BayeuxEventResponsePayload getData() {
        return data;
    }
    
    public String getField(String fieldName) {
    	return data.getField(fieldName);
    }
    
    public Boolean getBooleanField(String fieldName) {
        return Boolean.parseBoolean(data.getField(fieldName));
    }
    
    public Integer getIntegerField(String fieldName) {
        return Integer.parseInt(data.getField(fieldName));
    }
    
    public Long getLongField(String fieldName) {
        return Long.parseLong(data.getField(fieldName));
    }
    
    public String getSalesforceId() {
        return data.getId();
    }
    
    public String getEventType() {
        return this.data.getEvent().getType();
    }

    public String getCreatedDate() {
        return this.data.getEvent().getCreatedDate();
    }
    
    /**
     * 
     * @author gwester
     * @since 174
     */
    public static class BayeuxEventResponsePayload {
    	private BayeuxEventMetadata event;
    	private Map<String, String> sobject;
    	
		public BayeuxEventMetadata getEvent() {
			return event;
		}
		public void setEvent(BayeuxEventMetadata event) {
			this.event = event;
		}
		public String getId() {
			return sobject.get("Id");
		}
		public String getName() {
			return sobject.get("Name");
		}
		public String getField(String fieldName) {
			return sobject.get(fieldName);
		}
		public void setSobject(Map<String, String>  sobject) {
			this.sobject = sobject;
		}
    }
    
    /**
     * 
     * @author gwester
     * @since 174
     */
    public static class BayeuxEventMetadata {
    	private String type;
    	private String createdDate;
    	
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(String createdDate) {
			this.createdDate = createdDate;
		}
    }
 
}
package test.util.api.streaming;

public abstract class StreamingApiRequest extends StreamingApiMessage {
    
    public abstract String getChannel();
    
    public abstract void setId(String id);
    
    public abstract void setExt(String ext);
    
    /**
     * Every non null private member variable will be returned in JSON format.
     * 
     * @return A JSON representation of this object.
     */
    public String toJson() {
        return gson.toJson(this);
    }
}

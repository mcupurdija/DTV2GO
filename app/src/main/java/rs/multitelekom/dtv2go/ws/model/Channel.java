package rs.multitelekom.dtv2go.ws.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "tv_channel")
public class Channel {

    @Element(name = "id", required = false)
    private String id;

    @Element(name = "streaming_url")
    private String streaming_url;

    @Element(name = "caption")
    private String caption;

    @Element(name = "icon_path", required = false)
    private String icon_path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreaming_url() {
        return streaming_url;
    }

    public void setStreaming_url(String streaming_url) {
        this.streaming_url = streaming_url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getIcon_path() {
        return icon_path;
    }

    public void setIcon_path(String icon_path) {
        this.icon_path = icon_path;
    }

    @Override
    public String toString() {
        return "Channel [id = " + id + ", streaming_url = " + streaming_url + ", caption = " + caption + ", icon_path = " + icon_path + "]";
    }
}

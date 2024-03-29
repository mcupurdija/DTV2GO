package rs.multitelekom.dtv2go.ws.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "tv_channels")
public class ChannelsData {

    @ElementList(entry = "tv_channel", inline = true, required = false)
    private List<Channel> channels;

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }
}

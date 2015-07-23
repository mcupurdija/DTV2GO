package rs.multitelekom.dtv2go.ws.model;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.HashMap;
import java.util.Map;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
        "device3",
        "device1",
        "device2"
})
public class Devices {

    @JsonProperty("device3")
    private Device3 device3;
    @JsonProperty("device1")
    private Device1 device1;
    @JsonProperty("device2")
    private Device2 device2;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The device3
     */
    @JsonProperty("device3")
    public Device3 getDevice3() {
        return device3;
    }

    /**
     * @param device3 The device3
     */
    @JsonProperty("device3")
    public void setDevice3(Device3 device3) {
        this.device3 = device3;
    }

    /**
     * @return The device1
     */
    @JsonProperty("device1")
    public Device1 getDevice1() {
        return device1;
    }

    /**
     * @param device1 The device1
     */
    @JsonProperty("device1")
    public void setDevice1(Device1 device1) {
        this.device1 = device1;
    }

    /**
     * @return The device2
     */
    @JsonProperty("device2")
    public Device2 getDevice2() {
        return device2;
    }

    /**
     * @param device2 The device2
     */
    @JsonProperty("device2")
    public void setDevice2(Device2 device2) {
        this.device2 = device2;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

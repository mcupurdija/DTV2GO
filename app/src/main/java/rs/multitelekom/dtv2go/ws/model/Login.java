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
        "session",
        "ip",
        "paymenttype",
        "active",
        "name",
        "id",
        "devices",
        "error"
})
public class Login {

    @JsonProperty("session")
    private String session;
    @JsonProperty("ip")
    private String ip;
    @JsonProperty("paymenttype")
    private String paymenttype;
    @JsonProperty("active")
    private Integer active;
    @JsonProperty("name")
    private String name;
    @JsonProperty("id")
    private String id;
    @JsonProperty("devices")
    private Devices devices;
    @JsonProperty("error")
    private String error;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The session
     */
    @JsonProperty("session")
    public String getSession() {
        return session;
    }

    /**
     * @param session The session
     */
    @JsonProperty("session")
    public void setSession(String session) {
        this.session = session;
    }

    /**
     * @return The ip
     */
    @JsonProperty("ip")
    public String getIp() {
        return ip;
    }

    /**
     * @param ip The ip
     */
    @JsonProperty("ip")
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return The paymenttype
     */
    @JsonProperty("paymenttype")
    public String getPaymenttype() {
        return paymenttype;
    }

    /**
     * @param paymenttype The paymenttype
     */
    @JsonProperty("paymenttype")
    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
    }

    /**
     * @return The active
     */
    @JsonProperty("active")
    public Integer getActive() {
        return active;
    }

    /**
     * @param active The active
     */
    @JsonProperty("active")
    public void setActive(Integer active) {
        this.active = active;
    }

    /**
     * @return The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The devices
     */
    @JsonProperty("devices")
    public Devices getDevices() {
        return devices;
    }

    /**
     * @param devices The devices
     */
    @JsonProperty("devices")
    public void setDevices(Devices devices) {
        this.devices = devices;
    }

    /**
     * @return The error
     */
    @JsonProperty("error")
    public String getError() {
        return error;
    }

    /**
     * @param error The error
     */
    @JsonProperty("error")
    public void setError(String error) {
        this.error = error;
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

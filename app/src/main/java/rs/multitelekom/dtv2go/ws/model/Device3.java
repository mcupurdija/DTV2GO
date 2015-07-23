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
        "created",
        "imei",
        "updated",
        "type",
        "id",
        "mac"
})
public class Device3 {

    @JsonProperty("created")
    private String created;
    @JsonProperty("imei")
    private String imei;
    @JsonProperty("updated")
    private String updated;
    @JsonProperty("type")
    private String type;
    @JsonProperty("id")
    private String id;
    @JsonProperty("mac")
    private String mac;
    @JsonIgnore
    private Map<String, String> additionalProperties = new HashMap<String, String>();

    /**
     * @return The created
     */
    @JsonProperty("created")
    public String getCreated() {
        return created;
    }

    /**
     * @param created The created
     */
    @JsonProperty("created")
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * @return The imei
     */
    @JsonProperty("imei")
    public String getImei() {
        return imei;
    }

    /**
     * @param imei The imei
     */
    @JsonProperty("imei")
    public void setImei(String imei) {
        this.imei = imei;
    }

    /**
     * @return The updated
     */
    @JsonProperty("updated")
    public String getUpdated() {
        return updated;
    }

    /**
     * @param updated The updated
     */
    @JsonProperty("updated")
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    /**
     * @return The type
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
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
     * @return The mac
     */
    @JsonProperty("mac")
    public String getMac() {
        return mac;
    }

    /**
     * @param mac The mac
     */
    @JsonProperty("mac")
    public void setMac(String mac) {
        this.mac = mac;
    }

    @JsonAnyGetter
    public Map<String, String> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, String value) {
        this.additionalProperties.put(name, value);
    }

}

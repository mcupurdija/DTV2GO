package rs.multitelekom.dtv2go.ws.model;

public class Registration {

    private String password;
    private String imei;
    private String type;
    private String device;
    private String active;

    public Registration() {
    }

    public Registration(String password, String imei, String type, String device, String active) {
        super();
        this.password = password;
        this.imei = imei;
        this.type = type;
        this.device = device;
        this.active = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}

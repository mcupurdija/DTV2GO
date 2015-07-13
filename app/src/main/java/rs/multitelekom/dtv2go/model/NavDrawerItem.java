package rs.multitelekom.dtv2go.model;

public class NavDrawerItem {

    private String title;
    private int icon;
    private boolean isUserId = false;
    private boolean isHeader = false;
    private boolean isCounterVisible = false;
    private String count = "0";

    public NavDrawerItem() {
    }

    public NavDrawerItem(String title, int icon, boolean isUserId, boolean isHeader) {
        this.title = title;
        this.icon = icon;
        this.isUserId = isUserId;
        this.isHeader = isHeader;
    }

    public NavDrawerItem(String title, int icon, boolean isUserId, boolean isHeader, boolean isCounterVisible, String count) {
        this.title = title;
        this.icon = icon;
        this.isUserId = isUserId;
        this.isHeader = isHeader;
        this.isCounterVisible = isCounterVisible;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isUserId() {
        return isUserId;
    }

    public void setUserId(boolean isUserId) {
        this.isUserId = isUserId;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean isHeader) {
        this.isHeader = isHeader;
    }

    public boolean isCounterVisible() {
        return isCounterVisible;
    }

    public void setCounterVisible(boolean isCounterVisible) {
        this.isCounterVisible = isCounterVisible;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}

package rs.multitelekom.dtv2go.util;

public class AppConstants {

    public static final String SHARED_PREFERENCES = "dtv2go_preferences";

    public static final String WEB_API = "http://1308.eu01.aws.af.cm/korisnici/";
    public static final String WEB_API_D = "http://1308.eu01.aws.af.cm/korisnici/devid/";

    public static final String IPTV_API = "http://46.40.5.100:88/iptvapi/user/";
    public static final String LOGIN_URL = IPTV_API + "%s?password=%s";
    public static final String REGISTRATION_URL = IPTV_API + "%s";
    public static final String CONTACT_URL = "http://moj.stcable.net/index.php?s=ott&c=%s";

    public static final String CHANNELS_SD_URL = "http://www.stcable.tv/ott/android_app_list.xml";
    public static final String CHANNELS_HD_URL = "http://www.stcable.tv/ott/android_app_list_720.xml";
    public static final String MOVIES_URL = "http://www.stcable.tv/ott/vod_list.xml";

}

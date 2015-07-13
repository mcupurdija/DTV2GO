package rs.multitelekom.dtv2go.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String CONTENT_AUTHORITY = "rs.multitelekom.dtv2go.database_provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_CHANNELS = "Channels";
    private static final String PATH_NAME = "name";

    public interface ChannelsColumns {
        String CHANNEL_NAME = "name";
        String CHANNEL_ICON_URI = "icon_uri";
        String CHANNEL_VIDEO_URI = "video_uri";
    }

    public static class Channels implements ChannelsColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CHANNELS).build();
        public static final Uri CONTENT_FILTER_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CHANNELS).appendPath(PATH_NAME).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.rs.multitelekom.dtv2go.Channels";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rs.multitelekom.dtv2go.Channels";

        public static final String SORT_BY_NAME = CHANNEL_NAME + " ASC";

        public static Uri buildIdUri(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }
    }
}

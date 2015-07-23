package rs.multitelekom.dtv2go.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String CONTENT_AUTHORITY = "rs.multitelekom.dtv2go.database_provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_CHANNELS = "Channels";
    private static final String PATH_FAVOURITES = "Favourites";
    private static final String PATH_MOVIES = "Movies";
    private static final String PATH_NAME = "name";

    public interface ChannelsColumns {
        String CHANNEL_ID = "ID";
        String CHANNEL_NAME = "Name";
        String CHANNEL_ICON_URI = "Icon_URI";
        String CHANNEL_VIDEO_URI = "Video_URI";
        String QUALITY = "Quality";
    }

    public interface FavouritesColumns {
        String CHANNEL_ID = "ID";
        String CHANNEL_NAME = "Name";
        String CHANNEL_ICON_URI = "Icon_URI";
        String CHANNEL_VIDEO_URI = "Video_URI";
        String QUALITY = "Quality";
    }

    public interface MoviesColumns {
        String MOVIE_TITLE = "Title";
        String MOVIE_DESCRIPTION = "Description";
        String MOVIE_DURATION = "Duration";
        String MOVIE_GENRE = "Genre";
        String MOVIE_POSTER = "Poster";
        String MOVIE_VIDEO_URI = "Video_URI";
        String MOVIE_SUBTITLE = "Subtitle";
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

    public static class Favourites implements FavouritesColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITES).build();
        public static final Uri CONTENT_FILTER_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITES).appendPath(PATH_NAME).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.rs.multitelekom.dtv2go.Favourites";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rs.multitelekom.dtv2go.Favourites";

        public static final String SORT_BY_NAME = CHANNEL_NAME + " ASC";

        public static Uri buildIdUri(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }
    }

    public static class Movies implements MoviesColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.rs.multitelekom.dtv2go.Movies";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rs.multitelekom.dtv2go.Movies";

        public static final String SORT_BY_TITLE = MOVIE_TITLE + " ASC";

        public static Uri buildMoviesFilterUri(String query, int genre) {
            return CONTENT_URI.buildUpon().appendPath(PATH_NAME).appendPath(query).appendPath(String.valueOf(genre)).build();
        }

        public static Uri buildIdUri(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }
    }
}

package rs.multitelekom.dtv2go.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import java.util.List;

import rs.multitelekom.dtv2go.ui.vod.Genres;

public class DatabaseProvider extends ContentProvider {

    private static final String TAG = "DTV2GO_DatabaseProvider";
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    // Used for the UriMacher
    private static final int CHANNELS = 10;
    private static final int CHANNELS_ID = 11;
    private static final int CHANNELS_SEARCH_NAME = 12;
    private static final int FAVOURITES = 20;
    private static final int FAVOURITES_ID = 21;
    private static final int FAVOURITES_SEARCH_NAME = 22;
    private static final int MOVIES = 30;
    private static final int MOVIES_ID = 31;
    private static final int MOVIES_SEARCH_NAME = 32;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        String authority = DatabaseContract.CONTENT_AUTHORITY;
        uriMatcher.addURI(authority, "Channels", CHANNELS);
        uriMatcher.addURI(authority, "Channels/#", CHANNELS_ID);
        uriMatcher.addURI(authority, "Channels/name/#", CHANNELS_SEARCH_NAME);
        uriMatcher.addURI(authority, "Channels/name/*", CHANNELS_SEARCH_NAME);
        uriMatcher.addURI(authority, "Favourites", FAVOURITES);
        uriMatcher.addURI(authority, "Favourites/#", FAVOURITES_ID);
        uriMatcher.addURI(authority, "Favourites/name/#", FAVOURITES_SEARCH_NAME);
        uriMatcher.addURI(authority, "Favourites/name/*", FAVOURITES_SEARCH_NAME);
        uriMatcher.addURI(authority, "Movies", MOVIES);
        uriMatcher.addURI(authority, "Movies/#", MOVIES_ID);
        uriMatcher.addURI(authority, "Movies/name/#/#", MOVIES_SEARCH_NAME);
        uriMatcher.addURI(authority, "Movies/name/*/#", MOVIES_SEARCH_NAME);
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext(), null, null, 0);
        databaseHelper.getWritableDatabase();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "QUERY: " + uri);

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        String query, groupBy = null;

        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case CHANNELS:
                queryBuilder.setTables(Tables.CHANNELS);
                break;
            case CHANNELS_ID:
                query = uri.getLastPathSegment();
                queryBuilder.setTables(Tables.CHANNELS);
                queryBuilder.appendWhere(DatabaseContract.Channels._ID + "=" + query);
                break;
            case CHANNELS_SEARCH_NAME:
                query = uri.getLastPathSegment();
                queryBuilder.setTables(Tables.CHANNELS);
                queryBuilder.appendWhere(DatabaseContract.Channels.CHANNEL_NAME + " LIKE '%" + query + "%'");
                break;
            case FAVOURITES:
                queryBuilder.setTables(Tables.FAVOURITES);
                break;
            case FAVOURITES_ID:
                query = uri.getLastPathSegment();
                queryBuilder.setTables(Tables.FAVOURITES);
                queryBuilder.appendWhere(DatabaseContract.Favourites._ID + "=" + query);
                break;
            case FAVOURITES_SEARCH_NAME:
                query = uri.getLastPathSegment();
                queryBuilder.setTables(Tables.FAVOURITES);
                queryBuilder.appendWhere(DatabaseContract.Favourites.CHANNEL_NAME + " LIKE '%" + query + "%'");
                break;
            case MOVIES:
                queryBuilder.setTables(Tables.MOVIES);
                break;
            case MOVIES_ID:
                query = uri.getLastPathSegment();
                queryBuilder.setTables(Tables.MOVIES);
                queryBuilder.appendWhere(DatabaseContract.Movies._ID + "=" + query);
                break;
            case MOVIES_SEARCH_NAME:
                List<String> pathSegments = uri.getPathSegments();
                query = pathSegments.get(2);
                int genre = Integer.valueOf(pathSegments.get(3));
                queryBuilder.setTables(Tables.MOVIES);
                if (!query.equals("null")) {
                    queryBuilder.appendWhere(DatabaseContract.Movies.MOVIE_TITLE + " LIKE '%" + query + "%' AND ");
                }
                switch (genre) {
                    case 1:
                        queryBuilder.appendWhere(DatabaseContract.Movies.MOVIE_GENRE + "='" + Genres.Akcija.name() + "'");
                        break;
                    case 2:
                        queryBuilder.appendWhere(DatabaseContract.Movies.MOVIE_GENRE + "='" + Genres.Avantura.name() + "'");
                        break;
                    case 3:
                        queryBuilder.appendWhere(DatabaseContract.Movies.MOVIE_GENRE + "='" + Genres.Komedija.name() + "'");
                        break;
                    case 4:
                        queryBuilder.appendWhere(DatabaseContract.Movies.MOVIE_GENRE + "='" + Genres.Drama.name() + "'");
                        break;
                    case 5:
                        queryBuilder.appendWhere(DatabaseContract.Movies.MOVIE_GENRE + "='" + Genres.Fantazija.name() + "'");
                        break;
                    case 6:
                        queryBuilder.appendWhere(DatabaseContract.Movies.MOVIE_GENRE + "='" + Genres.Horor.name() + "'");
                        break;
                    case 7:
                        queryBuilder.appendWhere(DatabaseContract.Movies.MOVIE_GENRE + "='" + Genres.Misterija.name() + "'");
                        break;
                    case 8:
                        queryBuilder.appendWhere(DatabaseContract.Movies.MOVIE_GENRE + "='" + Genres.Romantika.name() + "'");
                        break;
                    case 9:
                        queryBuilder.appendWhere(DatabaseContract.Movies.MOVIE_GENRE + "='" + Genres.Triler.name() + "'");
                        break;
                    default:
                        queryBuilder.appendWhere("1");
                        break;
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        Cursor cursor = queryBuilder.query(databaseHelper.getReadableDatabase(), projection, selection, selectionArgs, groupBy, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Log.d(TAG, "INSERT: " + uri);

        database = databaseHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        long id;
        switch (match) {
            case CHANNELS:
                id = database.insertOrThrow(Tables.CHANNELS, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return DatabaseContract.Channels.buildIdUri(String.valueOf(id));
            case FAVOURITES:
                id = database.insertOrThrow(Tables.FAVOURITES, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return DatabaseContract.Favourites.buildIdUri(String.valueOf(id));
            case MOVIES:
                id = database.insertOrThrow(Tables.MOVIES, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return DatabaseContract.Movies.buildIdUri(String.valueOf(id));
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        Log.d(TAG, "DELETE: " + uri);

        database = databaseHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int rows;
        switch (match) {
            case CHANNELS:
                rows = database.delete(Tables.CHANNELS, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return rows;
            case FAVOURITES:
                rows = database.delete(Tables.FAVOURITES, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return rows;
            case MOVIES:
                rows = database.delete(Tables.MOVIES, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return rows;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        Log.d(TAG, "UPDATE: " + uri);

        database = databaseHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int rows;
        switch (match) {
            case CHANNELS:
                rows = database.update(Tables.CHANNELS, values, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return rows;
            case FAVOURITES:
                rows = database.update(Tables.FAVOURITES, values, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return rows;
            case MOVIES:
                rows = database.update(Tables.MOVIES, values, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return rows;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}

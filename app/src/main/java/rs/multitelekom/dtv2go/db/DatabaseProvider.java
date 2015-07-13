package rs.multitelekom.dtv2go.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class DatabaseProvider extends ContentProvider {

    private static final String TAG = "DTV2GO_DatabaseProvider";
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    // Used for the UriMacher
    private static final int CHANNELS = 10;
    private static final int CHANNELS_ID = 11;
    private static final int CHANNELS_SEARCH_NAME = 12;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        String authority = DatabaseContract.CONTENT_AUTHORITY;
        uriMatcher.addURI(authority, "Channels", CHANNELS);
        uriMatcher.addURI(authority, "Channels/#", CHANNELS_ID);
        uriMatcher.addURI(authority, "Channels/name/#", CHANNELS_SEARCH_NAME);
        uriMatcher.addURI(authority, "Channels/name/*", CHANNELS_SEARCH_NAME);
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
                id = database.insertWithOnConflict(Tables.CHANNELS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                getContext().getContentResolver().notifyChange(uri, null);
                return DatabaseContract.Channels.buildIdUri(String.valueOf(id));
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
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        Log.d(TAG, "UPDATE: " + uri);

        database = databaseHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int rows = 0;
        switch (match) {
            case CHANNELS:
                rows = database.update(Tables.CHANNELS, values, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return rows;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}

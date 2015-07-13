package rs.multitelekom.dtv2go.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;

import rs.multitelekom.dtv2go.util.AssetUtils;
import rs.multitelekom.dtv2go.util.SqlParser;
import rs.multitelekom.dtv2go.util.VersionUtils;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    // Sub path in assets directory for sql files
    private static final String SQL_DIR = "sql";
    // Init sql file
    private static final String CREATEFILE = "create.sql";
    // Upgrade Sql File prefix
    private static final String UPGRADEFILE_PREFIX = "upgrade-";
    // Upgrade Sql File suffix
    private static final String UPGRADEFILE_SUFFIX = ".sql";
    // Database file name
    private static final String DATABASE_NAME = "dtv2go.s3db";
    private Context context;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, VersionUtils.getVersionCode(context));
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            execSqlFile(CREATEFILE, db);
            Log.i(TAG, "Database created");
        } catch (IOException exception) {
            Log.e(TAG, "Database creation failed");
            Log.e(TAG, exception.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "Database upgrading");
        try {
            for (String sqlFile : AssetUtils.list(SQL_DIR, this.context.getAssets())) {
                if (sqlFile.startsWith(UPGRADEFILE_PREFIX)) {
                    int fileVersion = Integer.parseInt(sqlFile.substring(UPGRADEFILE_PREFIX.length(), sqlFile.length() - UPGRADEFILE_SUFFIX.length()));
                    if (fileVersion > oldVersion && fileVersion <= newVersion) {
                        execSqlFile(sqlFile, db);
                    } else {
                        Log.i(TAG, "Skipping upgrade version " + String.valueOf(fileVersion));
                    }
                }
            }
            Log.i(TAG, "Database upgraded to version " + String.valueOf(newVersion));
        } catch (IOException exception) {
            Log.e(TAG, "Database upgrade failed");
        }
    }

    protected void execSqlFile(String sqlFile, SQLiteDatabase db) throws SQLException, IOException {
        for (String sqlInstruction : SqlParser.parseSqlFile(SQL_DIR + "/" + sqlFile, this.context.getAssets())) {
            db.execSQL(sqlInstruction);
        }
    }
}

package edu.calpoly.aagrover.goals;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by ashleygrover on 5/11/16.
 */
public class GoalsContentProvider extends ContentProvider {

    /** The goals database. */
    private GoalsDatabaseHelper database;

    /** Values for the URIMatcher. */
    private static final int GOAL_ID = 1;

    /** The authority for this content provider. */
    private static final String AUTHORITY = "edu.calpoly.aagrover.goals.contentprovider";

    /** The database table to read from and write to; root path. */
    private static final String BASE_PATH = "goal_table";

    /** This provider's content location. Used by accessing applications
     * to interact with this provider.
     */
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/goals/#", GOAL_ID);
    }

    @Override
    public boolean onCreate() {
        database = new GoalsDatabaseHelper(getContext(), GoalsDatabaseHelper.GOALS_DATABASE, null, GoalsDatabaseHelper.DATABASE_VERSION);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        /* Use a helper class to perform a query for us. */
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        Log.w("HERE", "MADE IT");

        checkColumns(projection);
        /* Set up helper to query our jokes table. */
        queryBuilder.setTables(GoalsTable.DATABASE_TABLE_GOALS);

        /* Match the passed-in URI to an expected URI format. */
        int uriType = sURIMatcher.match(uri);

        // TODO: possibly switch case.

        /* Perform the database query. */
        SQLiteDatabase db = this.database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, null, null, null, null);

        /* Set the cursor to automatically alert listeners for content/view refreshing. */
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        /* Open the database for writing. */
        SQLiteDatabase db = this.database.getWritableDatabase();

        /* Will contain the ID of the inserted joke. */
        long id = 0;

        /*Match the passed-in URI to an expected URI format. */
        int uriType = sURIMatcher.match(uri);

        switch(uriType) {
            case GOAL_ID:
                id = db.insert(GoalsTable.DATABASE_TABLE_GOALS, null, values);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        /* Alert any watchers of an underlying data change for content/view refreshing. */
        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = this.database.getWritableDatabase();
        int rowsDeleted = 0;

        int uriType = sURIMatcher.match(uri);

        switch (uriType) {
            case GOAL_ID:
                String goalID = uri.getLastPathSegment();
                rowsDeleted = db.delete(GoalsTable.DATABASE_TABLE_GOALS, GoalsTable.GOAL_KEY_ID + "=" + goalID, null);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (rowsDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = this.database.getWritableDatabase();
        int updated = 0;
        int uriType = sURIMatcher.match(uri);

        switch (uriType) {
            case GOAL_ID:
                String goalID = uri.getLastPathSegment();
                updated = db.update(GoalsTable.DATABASE_TABLE_GOALS, values, GoalsTable.GOAL_KEY_ID + "=" + goalID, null);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return updated;
    }

    /**
     * Verfieis the correct set of columns to return data from when performing a query.
     * @param projection - the set of columns about to be queried.
     */
    private void checkColumns(String[] projection) {
        String[] available = { GoalsTable.GOAL_KEY_ID, GoalsTable.GOAL_KEY_TEXT, GoalsTable.GOAL_KEY_DATE };

        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));

            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }
}

package edu.calpoly.aagrover.goal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * Created by ashleygrover on 5/11/16.
 */
public class GoalsDatabaseHelper extends android.database.sqlite.SQLiteOpenHelper {

    /** The name of the database. */
    public static final String GOALS_DATABASE = "gdatabase.db";

    /** The starting database version. */
    public static final int DATABASE_VERSION = 1;

    /**
     * Create a helper object to create, open, and/or manage a database.
     * @param context - the application context.
     * @param name - The name of the database.
     * @param factory - Factory used to create a cursor. Null for default.
     * @param version - The starting database version.
     */
    public GoalsDatabaseHelper(Context context, String name,
                               CursorFactory factory, int version) {

        super(context, GOALS_DATABASE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        GoalsTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        GoalsTable.onUpgrade(db, oldVersion, newVersion);
    }
}

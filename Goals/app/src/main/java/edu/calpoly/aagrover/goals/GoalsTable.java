package edu.calpoly.aagrover.goals;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by ashleygrover on 5/11/16.
 */
public class GoalsTable {

    /** Goals table in the database. */
    public static final String DATABASE_TABLE_GOALS = "goal_table";

    /** Goals table column names and IDs for database access. */
    public static final String GOAL_KEY_ID = "_id";
    public static final int GOAL_COL_ID = 0;

    public static final String GOAL_KEY_TEXT = "goal_text";
    public static final int GOAL_COL_TEXT = GOAL_COL_ID + 1;

    public static final String GOAL_KEY_DATE = "date";
    public static final int GOAL_COL_DATE = GOAL_COL_ID + 2;

    /**
     * SQLite database creation statement. Auto-increments IDs of inserted goals.
     * Goal IDs are set after insertion into the database.
     */
    public static final String DATABASE_CREATE = "create table " + DATABASE_TABLE_GOALS + " (" +
            GOAL_KEY_ID + " integer primary key autoincrement, " +
            GOAL_KEY_TEXT + " text not null, " +
            GOAL_KEY_DATE + " text not null);";

    /** SQLite database table removal statement. Only used if upgrading database. */
    public static final String DATABASE_DROP = "drop table if exists " + DATABASE_TABLE_GOALS;

    /** This initializes the database.
     *
     * @param database - the database to initialize.
     */
    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    /**
     *  This upgrades the database to a new version.
     *  @param database - the database to upgrade.
     *  @param oldVersion - the old version of the database.
     *  @param newVersion - the new version of the database.
     */
    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(GoalsTable.class.getName(), "Database being upgraded from " + oldVersion + " to " + newVersion);
        database.execSQL(DATABASE_DROP);
        onCreate(database);
    }
}

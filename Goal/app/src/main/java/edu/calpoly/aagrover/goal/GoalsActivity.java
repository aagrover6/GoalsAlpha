package edu.calpoly.aagrover.goal;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class GoalsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, GoalView.OnGoalChangeListener {

    protected GoalCursorAdapter goalAdapter;
    protected ListView goalLayout;
    protected Button startGoalButton;

    protected static final String SAVED_EDIT_TEXT = "goalEditText";
    protected static final String SAVED_DATE_VALUE = "dateEditText";
    public static String percentage;

    /* Not exactly sure what this means.. It is the ID of the CursorLoader to be
    initialized in the LoaderManager and used to load a Cursor. */
    private static final int LOADER_ID = 1;
    private int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("My Goals");

        this.goalAdapter = new GoalCursorAdapter(this, null, 0);
        this.goalAdapter.setOnGoalChangeListener(this);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        initLayout();
        initAddGoalListeners();

        if (getIntent().hasExtra("goalText") && getIntent().hasExtra("dateText")) {
            String theGoal = getIntent().getStringExtra("goalText");
            String theDate = getIntent().getStringExtra("dateText");
            String currentDate = getIntent().getStringExtra("currentDate");
            percentage = getIntent().getStringExtra("percentage");

            Bundle extra = getIntent().getExtras();

            Log.w("currentDate", currentDate);

            if(getIntent().hasExtra("goal") && getIntent().hasExtra("date")) {
                getIntent().removeExtra("goal");
                getIntent().removeExtra("date");
                getIntent().removeExtra("curDate");
            }

            if (!theGoal.equals("")) {
                addGoal(new Goal(theGoal, theDate, currentDate));
                goalAdapter.notifyDataSetChanged();
            }
        }

        /* Retrieve and modify a set of name/value pairs associated with this activity. */
        SharedPreferences preferences = getPreferences(Activity.MODE_PRIVATE);

        /* Get the preference value SAVED_EDIT_TEXT if it exists, otherwise "" */
        String text = preferences.getString(SAVED_EDIT_TEXT, "");

        fillData();
    }

    protected void initLayout() {
        this.setContentView(R.layout.activity_goals);
        this.goalLayout = (ListView) this.findViewById(R.id.goalListViewGroup);

        if (this.goalLayout != null) {
            this.goalLayout.setAdapter(goalAdapter);
        }

        this.startGoalButton = (Button) this.findViewById(R.id.bStartNewGoal);

        goalLayout.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedPosition = position;
                GoalView gv = (GoalView)goalLayout.getChildAt(selectedPosition);
                Log.w("GoalView is ", "" +  gv);

                Goal goal = gv.getGoal();

                Intent i = new Intent(GoalsActivity.this, GoalInfoActivity.class);
                i.putExtra("goal", goal.getGoal());
                i.putExtra("date", goal.getDate());
                i.putExtra("curDate", goal.getCurDate());
                startActivity(i);
                goalAdapter.notifyDataSetChanged();

            }
        });
    }

    protected void initAddGoalListeners() {
        this.startGoalButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoalsActivity.this, StartNewGoalActivity.class);
                GoalsActivity.this.startActivity(intent);
            }
        });
    }

    protected void addGoal(Goal goal) {
        Uri uri = Uri.parse(GoalsContentProvider.CONTENT_URI.toString() + "/goals/" + goal.getID());
        ContentValues cv = new ContentValues();

        cv.put(GoalsTable.GOAL_KEY_TEXT, goal.getGoal());
        Log.w("Date is ", goal.getDate());
        cv.put(GoalsTable.GOAL_KEY_DATE, goal.getDate());
        cv.put(GoalsTable.GOAL_KEY_START, goal.getCurDate());

        Uri u = getContentResolver().insert(uri, cv);
        goal.setID(Long.valueOf(u.getLastPathSegment()));
        fillData();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.w("Loader", "" + id);
        String[] projection = { GoalsTable.GOAL_KEY_ID, GoalsTable.GOAL_KEY_TEXT,
                GoalsTable.GOAL_KEY_DATE, GoalsTable.GOAL_KEY_START};

        Uri uri = Uri.parse((GoalsContentProvider.CONTENT_URI.toString() + "/goals/" + LOADER_ID));
        // TODO: Not sure about uri... Idk.
        CursorLoader cursorLoader = new CursorLoader(this.getApplicationContext(), uri, projection, null, null, null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        /* Swap in a new Cursor, returning the old Cursor. */
        this.goalAdapter.swapCursor(data);
        this.goalAdapter.setOnGoalChangeListener(this);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        /* Swap in a new Cursor (null), returning the old Cursor. */
        this.goalAdapter.swapCursor(null);
    }

    @Override
    public void onGoalChanged(GoalView view, Goal goal) {
        Uri uri = Uri.parse(GoalsContentProvider.CONTENT_URI.toString() + "/goals/" + goal.getID());
        ContentValues cv = new ContentValues();

        cv.put(GoalsTable.GOAL_KEY_TEXT, goal.getGoal());
        cv.put(GoalsTable.GOAL_KEY_DATE, goal.getDate());
        cv.put(GoalsTable.GOAL_KEY_START, goal.getDate());

        this.getContentResolver().update(uri, cv, null, null);
        this.goalAdapter.setOnGoalChangeListener(null);

        fillData();
    }

    private void fillData() {
        this.getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
        this.goalLayout.setAdapter(this.goalAdapter);
    }
}


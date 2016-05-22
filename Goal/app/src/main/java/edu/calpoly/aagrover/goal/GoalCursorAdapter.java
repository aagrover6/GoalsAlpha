package edu.calpoly.aagrover.goal;

        import android.content.Context;
        import android.database.Cursor;
        import android.view.View;
        import android.view.ViewGroup;

        import edu.calpoly.aagrover.goal.GoalView.OnGoalChangeListener;

/**
 * Functions similarly to GoalsListAdapter. Uses a Cursor instead. The Cursor is a list
 * of rows from a database that acts as a medium between the database and a ViewGroup.
 * We have a SQLite database table and a ListView containing GoalViews.
 */
public class GoalCursorAdapter extends android.support.v4.widget.CursorAdapter {

    /* The OnGoalChangeListener that should be connected to each of the GoalViews
     * created/managed by this adapter. */
    private GoalView.OnGoalChangeListener listener;

    public GoalCursorAdapter(Context context, Cursor goalCursor, int flags) {
        super(context, goalCursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Goal newGoal = new Goal(cursor.getString(GoalsTable.GOAL_COL_TEXT),
                cursor.getString(GoalsTable.GOAL_COL_DATE),
                cursor.getString(GoalsTable.GOAL_COL_START),
                cursor.getLong(GoalsTable.GOAL_COL_ID));

        GoalView gv = new GoalView(context, newGoal);
        return gv;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Goal newGoal = new Goal(cursor.getString(GoalsTable.GOAL_COL_TEXT),
                cursor.getString(GoalsTable.GOAL_COL_DATE),
                cursor.getString(GoalsTable.GOAL_COL_START),
                cursor.getLong(GoalsTable.GOAL_COL_ID));

        GoalView gv = (GoalView)view;

        gv.setOnGoalChangeListener(null);
        gv.setGoal(newGoal);
        gv.setOnGoalChangeListener(this.listener);
    }

    public void setOnGoalChangeListener(OnGoalChangeListener mListener) {
        this.listener = mListener;
    }
}

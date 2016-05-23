package edu.calpoly.aagrover.goal;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by ashleygrover on 5/16/16.
 */
public class GoalView extends LinearLayout {

    /* Displays the goal text. */
    private TextView m_goalText;

    /* Displays the date text. */
    private TextView m_dateText;

    /* Data version of this View; contains goal info. */
    private Goal m_goal;

    private OnGoalChangeListener m_onGoalChangeListener;

    public GoalView(Context context, Goal goal) {
        super(context);

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.goal_view, this, true);

        this.m_goalText = (TextView)findViewById(R.id.goalTextView);
        this.m_dateText = (TextView)findViewById(R.id.dateTextView);

        this.setGoal(goal);
        m_onGoalChangeListener = null;
    }

    public void setGoal(Goal goal) {
        this.m_goal = goal;
        this.m_goalText.setText(m_goal.getGoal());
        this.m_dateText.setText(m_goal.getDate());

        String date = m_goal.getDate();

        String[] content = date.split("/");
        int year = Integer.parseInt(content[2]);
        int day = Integer.parseInt(content[1]);
        int month = Integer.parseInt(content[0]);

        Calendar c = new GregorianCalendar(year, month-1, day);
        long millis = c.getTimeInMillis();
        long difference = millis - Calendar.getInstance().getTime().getTime();
        if (difference <= 0) {
            this.m_dateText.setText("");
            this.m_dateText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check_30, 0, 0, 0);
        }



        /*this.m_dateText.setText(GoalsActivity.percentage);
        Log.w("percentage", "" + GoalsActivity.percentage);*/
    }

    //onCheckedChange method here.. Don't need it yet.

    public void setOnGoalChangeListener(OnGoalChangeListener listener) {
        this.m_onGoalChangeListener = listener;
    }

    protected void notifyOnGoalChangeListener() {
        if (this.m_onGoalChangeListener != null) {
            this.m_onGoalChangeListener.onGoalChanged(this, this.m_goal);
        }
    }

    public static interface OnGoalChangeListener {
        public void onGoalChanged(GoalView view, Goal goal);
    }

    public Goal getGoal() {
        return this.m_goal;
    }

}

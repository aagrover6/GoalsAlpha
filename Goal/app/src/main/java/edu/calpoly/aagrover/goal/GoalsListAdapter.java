package edu.calpoly.aagrover.goal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by ashleygrover on 5/13/16.
 */
public class GoalsListAdapter extends BaseAdapter {

    /* The application Context in which this GoalsListAdapter is being used. */
    private Context context;

    /* The data set to which this GoalsListAdapter is bound. */
    private List<Goal> goalList;

    /**
     * Takes in the application context in which it is being used and the Collection
     * of Goal objects to which it is bound.
     * @param context
     * @param goalList
     */
    public GoalsListAdapter(Context context, List<Goal> goalList) {
        this.context = context;
        this.goalList = goalList;
    }

    @Override
    public int getCount() {
        return this.goalList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.goalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoalView goalView = null;

        if (convertView == null) {
            goalView = new GoalView(this.context, this.goalList.get(position));
        }
        else {
            goalView = (GoalView)convertView;
        }

        goalView.setGoal(this.goalList.get(position));
        return goalView;
    }
}

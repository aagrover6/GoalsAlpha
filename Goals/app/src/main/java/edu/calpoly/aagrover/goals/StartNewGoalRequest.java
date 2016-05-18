package edu.calpoly.aagrover.goals;

import android.support.v4.util.ArrayMap;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by ashleygrover on 4/19/16.
 */
public class StartNewGoalRequest extends StringRequest {
    private static final String GOAL_REQUEST_URL = "http://aag.netne.net/Goal.php";
    private Map<String, String> params;

    public StartNewGoalRequest(String goal, String date, Response.Listener<String> listener) {
        super(Request.Method.POST, GOAL_REQUEST_URL,listener, null);
        params = new ArrayMap<>();
        params.put("goal", goal);
        params.put("date", date);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

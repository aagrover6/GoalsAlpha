package edu.calpoly.aagrover.goal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StartNewGoalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_new_goal);

        setTitle("Create a Goal");

        final EditText etUsername = (EditText)findViewById(R.id.etUsername);
        final EditText etTypeGoal = (EditText)findViewById(R.id.etTypeGoal);
        final EditText etTypeDate = (EditText)findViewById(R.id.etTypeDate);
        final Button bSetGoal = (Button)findViewById(R.id.bSetGoal);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date currentDate = new Date();
        String theDatee = dateFormat.format(currentDate);
        String[] dateInfo = theDatee.split(" ");

        final String theDate = dateInfo[0];

        Log.w("todays date is ", theDate);

        assert bSetGoal != null;
        bSetGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                assert etTypeGoal != null;
                assert etTypeDate != null;
                final String goal = etTypeGoal.getText().toString();
                final String date = etTypeDate.getText().toString();

                /* Return goal data to main goals activity. */
                Intent intent = new Intent(getApplicationContext(), GoalsActivity.class);
                intent.putExtra("goalText", goal);
                intent.putExtra("dateText", date);
                intent.putExtra("currentDate", theDate);
                intent.putExtra("percentage", "0.0");
                StartNewGoalActivity.this.startActivity(intent);
            }
        });
    }
}

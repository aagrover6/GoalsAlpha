package edu.calpoly.aagrover.goals;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StartNewGoalActivity extends AppCompatActivity {

    private String FILENAME = "GoalDate.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_new_goal);

        final EditText etUsername = (EditText)findViewById(R.id.etUsername);
        final EditText etTypeGoal = (EditText)findViewById(R.id.etTypeGoal);
        final EditText etTypeDate = (EditText)findViewById(R.id.etTypeDate);
        final Button bSetGoal = (Button)findViewById(R.id.bSetGoal);

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
                StartNewGoalActivity.this.startActivity(intent);
            }
        });
    }
}

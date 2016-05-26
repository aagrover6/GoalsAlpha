package edu.calpoly.aagrover.goal;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StartNewGoalActivity extends Activity implements OnClickListener {

    // UI References
    private EditText etTypeDate;
    private EditText etTypeGoal;

    private DatePickerDialog completionDate;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_new_goal);

        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        etTypeGoal = (EditText) findViewById(R.id.etTypeGoal);
        final Button bSetGoal = (Button)findViewById(R.id.bSetGoal);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date currentDate = new Date();
        String theDatee = dateFormat.format(currentDate);
        String[] dateInfo = theDatee.split(" ");

        final String theDate = dateInfo[0];

        findViewsById();
        setDateTimeField();

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

    private void findViewsById() {
        etTypeDate = (EditText) findViewById(R.id.etTypeDate);
        etTypeDate.setInputType(InputType.TYPE_NULL);
        etTypeDate.requestFocus();
    }

    private void setDateTimeField() {
        etTypeDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        completionDate = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etTypeDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    public void onClick(View view) {
        if(view == etTypeDate) {
            completionDate.show();
        }
    }
}
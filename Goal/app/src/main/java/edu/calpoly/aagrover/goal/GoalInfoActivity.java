package edu.calpoly.aagrover.goal;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class GoalInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_info);

        setTitle("The Details");

        final ProgressWheel pwOne = (ProgressWheel) findViewById(R.id.progressBarTwo);

        TextView tvGoal = (TextView) findViewById(R.id.tvGoal);
        TextView tvDate = (TextView) findViewById(R.id.tvDate);

        if(getIntent().hasExtra("goal") && getIntent().hasExtra("date")) {
            String theGoal = getIntent().getStringExtra("goal");
            String theDate = getIntent().getStringExtra("date");
            String startDate = getIntent().getStringExtra("curDate");

            tvGoal.setText(theGoal);
            tvDate.setText(theDate);

            // Get completed date to track progress.
            String[] content = theDate.split("/");
            int year = Integer.parseInt(content[2]);
            int day = Integer.parseInt(content[1]);
            int month = Integer.parseInt(content[0]);

            Calendar c = new GregorianCalendar(year, month-1, day);
            long millis = c.getTimeInMillis();
            long difference = millis - Calendar.getInstance().getTime().getTime();

            // Get start date to use for comparison.
            String[] content1 = startDate.split("/");
            year = Integer.parseInt(content1[0]);
            day = Integer.parseInt(content1[2]);
            month = Integer.parseInt(content1[1]);

            c = new GregorianCalendar(year, month-1, day);
            int totalDaysToCompleteGoal = (int) ((millis - c.getTimeInMillis()) / DateUtils.DAY_IN_MILLIS);

            Log.w("TIME DATE NOW IS", "" + Calendar.getInstance().getTime() + "" + Calendar.getInstance().getTime().getTime());
            Log.w("total days to complete", "" + totalDaysToCompleteGoal);

            countDownConfig(difference, totalDaysToCompleteGoal, pwOne);
        }
    }

    public void countDownConfig(long difference, int totalDaysToComplete, final ProgressWheel pwOne) {

        final TextView daysCount = (TextView) findViewById(R.id.tvDays);
        final TextView hoursCount = (TextView) findViewById(R.id.tvHours);
        final TextView minCount = (TextView) findViewById(R.id.tvMin);
        final TextView secCount = (TextView) findViewById(R.id.tvSec);

        final long total = (long)totalDaysToComplete;

        CountDownTimer cdt = new CountDownTimer(difference, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int days = 0;
                int hours = 0;
                int minutes = 0;
                int seconds = 0;
                int progress = 0;
                String sDate = "0";

                if (millisUntilFinished > DateUtils.DAY_IN_MILLIS) {
                    days = (int) (millisUntilFinished / DateUtils.DAY_IN_MILLIS);
                    sDate = "" + days;

                    float day = (float)days + 1;
                    float x = (day / (float)total) * 360;
                    progress = (360 - (int)x);

                    int p = (int)total - (int)day;

                    float percentage = p / (float)total;
                    pwOne.setProgress(progress);
                    pwOne.setText("" + String.format("%.1f", (percentage*100)) + " %");

                    GoalsActivity.percentage = String.valueOf(percentage) + "%";
                }

                millisUntilFinished -= (days * DateUtils.DAY_IN_MILLIS);

                if (millisUntilFinished > DateUtils.HOUR_IN_MILLIS) {
                    hours = (int) (millisUntilFinished / DateUtils.HOUR_IN_MILLIS);
                    if (progress == 0) {
                        pwOne.setProgress(0);
                        pwOne.setText("0 %");
                    }
                }

                millisUntilFinished -= (hours * DateUtils.HOUR_IN_MILLIS);

                if (millisUntilFinished > DateUtils.MINUTE_IN_MILLIS) {
                    minutes = (int) (millisUntilFinished / DateUtils.MINUTE_IN_MILLIS);
                }

                millisUntilFinished -= (minutes * DateUtils.MINUTE_IN_MILLIS);

                if (millisUntilFinished > DateUtils.SECOND_IN_MILLIS) {
                    seconds = (int) (millisUntilFinished / DateUtils.SECOND_IN_MILLIS);
                }

                String d = sDate;
                daysCount.setText(d);
                hoursCount.setText("" + String.format("%02d",hours));
                minCount.setText("" + String.format("%02d",minutes));
                secCount.setText("" + String.format("%02d",seconds));
            }

            @Override
            public void onFinish() {
                daysCount.setText("0");
                hoursCount.setText("0");
                minCount.setText("0");
                secCount.setText("0");

                pwOne.setProgress(360);
                pwOne.setText("100.0 %");

                TextView finished = (TextView) findViewById(R.id.tvProgress);
                finished.setText("Congrats! You completed your goal.");
            }
        };

        cdt.start();
    }
}

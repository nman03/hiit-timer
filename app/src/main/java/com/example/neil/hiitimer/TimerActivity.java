package com.example.neil.hiitimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {
    static int interval, counter;
    static int onDuration, offDuration, numOfSets;
    static boolean isOn = true;
    static Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        Intent intent = getIntent();
        int[] data = intent.getIntArrayExtra(MainActivity.EXTRA_MESSAGE);
        onDuration = data[0];
        offDuration = data[1];
        numOfSets = data[2];

        timer = new Timer();
        interval = onDuration;
        counter = 0;

        final TextView current = findViewById(R.id.textView);
        current.setText(String.valueOf(interval));

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        current.setText(String.valueOf(interval));
                        setInterval();
                    }
                });
            }
        }, 0, 1000);
    }

    @Override
    public void onBackPressed() {
        timer.cancel();
        timer.purge();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private static final void setInterval() {
        if (counter < numOfSets) {
            if (interval == 1) {
                if (isOn) {
                    interval = offDuration + 1;
                    isOn = false;
                }
                else {
                    interval = onDuration + 1;
                    isOn = true;
                    counter++;
                }
            }
        }
        else {
            timer.cancel();
            timer.purge();
        }

        interval--;
    }
}

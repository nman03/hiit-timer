package com.example.neil.hiitimer;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {
    static int interval, counter;
    static int onDuration, offDuration, numOfSets;
    static boolean isOn, isDone;
    static Timer timer;
    static MediaPlayer mp;
    static final int BEGIN_RES_ID = R.raw.begin;
    static final int REST_RES_ID = R.raw.rest;
    static final int DONE_RES_ID = R.raw.done;

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
        interval = 3;
        counter = 0;
        isOn = false;
        isDone = false;

        final TextView current = findViewById(R.id.textView);
        current.setText(String.valueOf(interval));

        final TextView setCounter = findViewById(R.id.setCounter);
        setCounter.setText(String.valueOf(counter));

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void run() {
                        resetInterval();

                        if (isDone) {
                            current.setText("Done");
                            mp.release();
                            timer.cancel();
                            timer.purge();
                        }

                        else {
                            current.setText(String.valueOf(interval));
                            if (interval == 3) {
                                if (isOn) {
                                    if (counter == numOfSets - 1) playVoiceQues(DONE_RES_ID);
                                    else playVoiceQues(REST_RES_ID);

                                }

                                else playVoiceQues(BEGIN_RES_ID);
                            }
                        }

                        setCounter.setText(String.valueOf(counter));
                        interval--;
                    }
                });
            }
        }, 0, 1000);
    }

    @Override
    public void onBackPressed() {
        timer.cancel();
        timer.purge();
        mp.release();
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


    private static final void resetInterval() {
        if (interval == 0) {
            if (!isDone) {
                if (isOn) {
                    interval = offDuration;
                    isOn = false;
                    counter++;
                    if (counter == numOfSets) isDone = true;
                } else {
                    interval = onDuration;
                    isOn = true;
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private final void playVoiceQues(int resourceId) {
        mp = new MediaPlayer();
        AssetFileDescriptor afd = getApplicationContext().getResources().openRawResourceFd(resourceId);

        try {
            mp.setDataSource(afd);
            mp.prepare();
            mp.start();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }


}

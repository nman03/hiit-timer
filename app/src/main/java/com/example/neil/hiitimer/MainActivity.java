package com.example.neil.hiitimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.neil.hiitimer.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startTimer(View view) {
        Intent intent = new Intent(this, TimerActivity.class);
        EditText on = findViewById(R.id.onDuration);
        int onDuration = Integer.parseInt(on.getText().toString());

        EditText off = findViewById(R.id.offDuration);
        int offDuration = Integer.parseInt(off.getText().toString());

        EditText sets = findViewById(R.id.numOfSets);
        int numOfSets = Integer.parseInt(sets.getText().toString());

        int[] data = new int[3];

        data[0] = onDuration;
        data[1] = offDuration;
        data[2] = numOfSets;

        intent.putExtra(EXTRA_MESSAGE, data);
        startActivity(intent);
    }

}

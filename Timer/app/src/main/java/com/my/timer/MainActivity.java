package com.my.timer;

import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewTimer;

    private int seconds = 0;
    private boolean isRunnig = false;
    private boolean wasRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewTimer = findViewById(R.id.textViewTimer);
        if(savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            isRunnig = savedInstanceState.getBoolean("isRunning");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        runTimer();
    }

    public void onClickStartTimer(View view) {
        isRunnig = true;
    }

    public void onClickStopTimer(View view) {
        isRunnig = false;
    }

    public void onClickResetTimer(View view) {
        isRunnig = false;
        seconds = 0;
    }



    private void runTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int sec = seconds % 60;

                String  time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, sec);
                mTextViewTimer.setText(time);

                if(isRunnig) {
                    seconds++;
                }

                handler.postDelayed(this, 1000);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("isRunning", isRunnig);
        outState.putBoolean("wasRunning", wasRunning);
    }


    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = isRunnig;
        isRunnig = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunnig = wasRunning;
    }
}

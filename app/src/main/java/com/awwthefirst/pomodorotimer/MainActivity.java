package com.awwthefirst.pomodorotimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView timerTextView;
    private CountDownTimer currentTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timer_text_view);
    }
    
    public void toggleTimer(View view) {
        Button button = (Button) view;

        if (currentTimer == null) { //Starts the timer
            workTimer();

            button.setText(R.string.end_timer_button);
        } else {
            currentTimer.cancel();
            currentTimer = null;

            timerTextView.setText(R.string.default_timer_text);
            button.setText(R.string.start_timer_button);
        }
    }

    private void workTimer() {
        currentTimer = createCountDownTimer(25, this::breakTimer);
        currentTimer.start();
    }

    private void breakTimer() {
        currentTimer = createCountDownTimer(5, this::workTimer);
        currentTimer.start();
    }

    private CountDownTimer createCountDownTimer(int minutes, Runnable runnable) {
         return new CountDownTimer(minutes * 60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Time time = new Time(millisUntilFinished);
                timerTextView.setText(time.toString());
            }

            @Override
            public void onFinish() {
                runnable.run();
            }
        };
    }
}
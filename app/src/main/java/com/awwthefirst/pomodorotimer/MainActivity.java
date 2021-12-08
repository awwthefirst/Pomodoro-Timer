package com.awwthefirst.pomodorotimer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String CHANNEL_ID = "0";
    private TextView timerTextView, modeTextView;
    private CountDownTimer currentTimer;
    private static final int WORK_TIME = 25, BREAK_TIME = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timer_text_view);
        modeTextView = findViewById(R.id.mode_text_view);

        createNotificationChannel();
    }
    
    public void toggleTimer(View view) {
        Button button = (Button) view;

        if (currentTimer == null) { //Starts the timer
            startWorkTimer();

            button.setText(R.string.end_timer_button);
        } else {
            currentTimer.cancel();
            currentTimer = null;

            timerTextView.setText(R.string.default_timer_text);
            modeTextView.setText(R.string.work_text);
            button.setText(R.string.start_timer_button);
        }
    }

    private void startWorkTimer() {
        currentTimer = createCountDownTimer(WORK_TIME, this::switchToBreakTimer);
        currentTimer.start();
        modeTextView.setText(R.string.work_text);
    }

    private void switchToWorkTimer() {
        startWorkTimer();
        sendNotification("Pomodoro Timer", "Back to work!");
    }

    private void switchToBreakTimer() {
        currentTimer = createCountDownTimer(BREAK_TIME, this::switchToWorkTimer);
        currentTimer.start();
        sendNotification("Pomodoro Timer", "Time for a break!");
        modeTextView.setText(R.string.break_text);
    }

    private CountDownTimer createCountDownTimer(int minutes, Runnable runnable) {
         return new CountDownTimer(minutes * 60000L, 1000) {

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

    private void sendNotification(String title, String text) { //TODO needs a icon
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[] {0, 250, 250, 250})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(0, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Timer finished";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setVibrationPattern(new long[] {0, 250, 250, 250});
            channel.enableVibration(true);
            channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
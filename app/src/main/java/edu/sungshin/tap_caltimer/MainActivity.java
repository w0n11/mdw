package edu.sungshin.tap_caltimer;

import android.app.TabActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.example.tap_caltimer.R;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

    private TextView countdownText;

    private Button startButton;
    private Button stopButton;
    private Button cancelButton;

    private EditText hourText;
    private EditText minText;
    private EditText secondText;

    private CountDownTimer countDownTimer;

    private boolean timerRunning;
    private boolean firstState;

    private long time = 0;
    private long tempTime = 0;

    FrameLayout setting;
    FrameLayout timer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = getTabHost();

        TabSpec tabSpecFirst = tabHost.newTabSpec("first").setIndicator("Food");

        tabSpecFirst.setContent(R.id.first);
        tabHost.addTab(tabSpecFirst);

        TabSpec tabSpecSecond = tabHost.newTabSpec("Calendar").setIndicator("Calendar");
        tabSpecSecond.setContent(R.id.CalendarView);
        tabHost.addTab(tabSpecSecond);

        TabSpec tabSpecThird = tabHost.newTabSpec("Timer").setIndicator("Timer");
        tabSpecThird.setContent(R.id.Timer);
        tabHost.addTab(tabSpecThird);

        tabHost.setCurrentTab(0);

        countdownText = findViewById(R.id.countdown_text);
        startButton = findViewById(R.id.countdown_button);
        stopButton = findViewById(R.id.stop_btn);
        cancelButton = findViewById(R.id.cancel_btn);

        hourText = findViewById(R.id.hour);
        minText = findViewById(R.id.min);
        secondText = findViewById(R.id.second);

        setting = findViewById(R.id.setting);
        timer = findViewById(R.id.timer);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstState = true;

                setting.setVisibility(setting.GONE);
                timer.setVisibility(timer.VISIBLE);
                startStop();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting.setVisibility(setting.VISIBLE);
                timer.setVisibility(timer.GONE);
                firstState = true;
                stopTimer();
            }
        });

        updateTimer();
    }

    private void startStop() {
        if (timerRunning) {
            stopTimer();
        }else{
            startTimer();
        }
    }

    private void startTimer() {
        if(firstState){
            String sHour = hourText.getText().toString();
            String sMin = minText.getText().toString();
            String sSecond = secondText.getText().toString();
            time = (Long.parseLong(sHour)*3600000)+(Long.parseLong(sMin)*60000)+(Long.parseLong(sSecond)*1000)+1000;
        }else{
            time = tempTime;
        }
        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tempTime = millisUntilFinished;
                updateTimer();
            }
            @Override
            public void onFinish() { }
        }.start();

        stopButton.setText("일시정지");
        timerRunning = true;
        firstState = false;
    }

    private void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        stopButton.setText("계속");
    }

    private void updateTimer() {
        int hour = (int) tempTime / 3600000;
        int minutes = (int) tempTime % 3600000 / 60000;
        int seconds = (int) tempTime % 3600000 % 60000 / 1000;

        String timeLeftText ="";

        timeLeftText = "" + hour + ":";

        if (minutes < 10) timeLeftText += "0";
        timeLeftText += minutes + ":";

        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        if(hourText.getText().toString().equals("")){
            hourText.setText("0");
        }
        if(minText.getText().toString().equals("")){
            minText.setText("0");
        }
        if(secondText.getText().toString().equals("")){
            secondText.setText("0");
        }
        countdownText.setText(timeLeftText);
    }

}

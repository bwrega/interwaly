package com.realizationtime.interwaly.trening;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.realizationtime.interwaly.ListOfInterwaly;
import com.realizationtime.interwaly.R;

import static com.realizationtime.interwaly.ListOfInterwaly.getFromString;
import static com.realizationtime.interwaly.trening.TimeFormatter.formatTenthsOfSecond;
import static com.realizationtime.interwaly.trening.TimeFormatter.formatWholeSeconds;


public class TreningActivity extends Activity {
    public static final String INTERWALY_PASS_KEY = "interwaly";
    private ListOfInterwaly interwaly;
    private ProgressBar currentProgress;
    private ProgressBar totalProgress;
    private Runnable updateUITask = new Runnable() {
        @Override
        public void run() {
            if (!treningThread.isAlive()){
                return;
            }
            currentProgress.setProgress(arbiter.getCurrentPercentage());
            totalProgress.setProgress(arbiter.getTotalPercentage());
            if (arbiter.isCurrentSprint()){
                findViewById(R.id.mainLayout).setBackgroundColor(Color.RED);
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
                ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(75);
            } else {
                findViewById(R.id.mainLayout).setBackgroundColor(Color.GREEN);
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            }
            updateCounterFields();
        }

        private void updateCounterFields() {
            runningCurrent.setText(formatTenthsOfSecond(arbiter.getCurrentTimeMS()));
            runningTotal.setText(formatWholeSeconds((int) arbiter.getCurrentInterval().getCzasMS()));
            totalCurrent.setText(formatTenthsOfSecond(arbiter.getTotalTimeMS()));
        }
    };
    private final Runnable onTreningDone = new Runnable() {
        @Override
        public void run() {
            finish();
        }
    };

    private Thread treningThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                while (!arbiter.isTreningDone()) {
                    runOnUiThread(updateUITask);
                    Thread.sleep(100);
                }
                runOnUiThread(onTreningDone);
            } catch (InterruptedException e) {
            }
        }
    });
    private TreningArbiter arbiter;
    private MediaPlayer mediaPlayer;
    private TextView runningCurrent;
    private TextView runningTotal;
    private TextView totalCurrent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trening);
        interwaly = getFromString(this, getIntent().getStringExtra(INTERWALY_PASS_KEY));
        arbiter = new TreningArbiter(interwaly);
        currentProgress = (ProgressBar) findViewById(R.id.currentProgress);
        totalProgress = (ProgressBar) findViewById(R.id.totalProgress);
        mediaPlayer = MediaPlayer.create(this, R.raw.beep);
        mediaPlayer.setLooping(true);
        initCounterFields();
        treningThread.start();
    }

    private void initCounterFields() {
        ((TextView) findViewById(R.id.totalTotal))
                .setText(formatWholeSeconds((int) interwaly.getTotalTimeInMS()));
        runningCurrent = (TextView) findViewById(R.id.runningCurrent);
        runningTotal = (TextView) findViewById(R.id.runningTotal);
        totalCurrent = (TextView) findViewById(R.id.totalCurrent);
    }

    public void onStopClicked(View view) {
        try {
            treningThread.interrupt();
        } catch (RuntimeException ex) {
            Log.i(getLocalClassName(), "exception while interrupting thread", ex);
        }
        finish();
    }

    @Override
    public void finish() {
        mediaPlayer.release();
        super.finish();
    }
}
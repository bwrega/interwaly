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
import com.realizationtime.interwaly.ListOfInterwaly;
import com.realizationtime.interwaly.R;

import static com.realizationtime.interwaly.ListOfInterwaly.getFromString;


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
        treningThread.start();
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
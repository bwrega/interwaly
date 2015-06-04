package com.realizationtime.interwaly.trening;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
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
            currentProgress.setProgress(arbiter.getCurrentPercentage());
            totalProgress.setProgress(arbiter.getTotalPercentage());
            if (arbiter.isCurrentSprint()){
                findViewById(R.id.mainLayout).setBackgroundColor(Color.RED);
            } else {
                findViewById(R.id.mainLayout).setBackgroundColor(Color.GREEN);
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trening);
        interwaly = getFromString(this, getIntent().getStringExtra(INTERWALY_PASS_KEY));
        arbiter = new TreningArbiter(interwaly);
        currentProgress = (ProgressBar) findViewById(R.id.currentProgress);
        totalProgress = (ProgressBar) findViewById(R.id.totalProgress);
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
}
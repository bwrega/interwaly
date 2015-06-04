package com.realizationtime.interwaly.trening;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.realizationtime.interwaly.ListOfInterwaly;
import com.realizationtime.interwaly.R;

import static com.realizationtime.interwaly.ListOfInterwaly.getFromString;


public class TreningActivity extends Activity {
    public static final String INTERWALY_PASS_KEY = "interwaly";
    private ListOfInterwaly interwaly;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trening);
        interwaly = getFromString(this, getIntent().getStringExtra(INTERWALY_PASS_KEY));
    }

    public void onStopClicked(View view) {
        finish();
    }
}
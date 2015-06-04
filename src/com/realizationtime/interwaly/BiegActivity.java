package com.realizationtime.interwaly;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import static com.realizationtime.interwaly.ListOfInterwaly.getFromString;


public class BiegActivity extends Activity {
    public static final String INTERWALY_PASS_KEY = "interwaly";
    private ListOfInterwaly interwaly;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bieg_layout);
        interwaly = getFromString(this, getIntent().getStringExtra(INTERWALY_PASS_KEY));
    }

    public void onStopClicked(View view) {
        finish();
    }
}
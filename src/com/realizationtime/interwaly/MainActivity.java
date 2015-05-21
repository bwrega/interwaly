package com.realizationtime.interwaly;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    List<Interwal> interwaly = new ArrayList<>();

    public void onAddBiegaj(View view) {
        Bieg bieg = new Bieg(15);
        interwaly.add(bieg);
        redrawIntervals();
    }

    public void onAddPrzerwa(View view) {
        Przerwa przerwa = new Przerwa(45);
        interwaly.add(przerwa);
        redrawIntervals();
    }

    private void redrawIntervals() {
        LinearLayout podInterwaly = (LinearLayout) findViewById(R.id.podInterwaly);
        podInterwaly.removeAllViews();
        for (Interwal interwal:interwaly) {
            Button nextButton = new Button(getApplicationContext());
            nextButton.setText(""+interwal.getCzas());
            if (interwal instanceof Bieg) {
                nextButton.setBackgroundColor(Color.RED);
            } else {
                //Przerwa
                nextButton.setBackgroundColor(Color.GREEN);
            }
            podInterwaly.addView(nextButton);
        }
    }
}

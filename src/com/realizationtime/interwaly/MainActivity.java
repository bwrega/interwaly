package com.realizationtime.interwaly;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    int licznik = 0;
    public void onAddBiegaj(View view) {
        LinearLayout podInterwaly = (LinearLayout) findViewById(R.id.podInterwaly);
        Button addedButton = new Button(getApplicationContext());
        licznik++;
        addedButton.setText("Dodany + "+licznik);
        podInterwaly.addView(addedButton);
    }
}

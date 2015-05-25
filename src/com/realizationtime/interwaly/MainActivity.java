package com.realizationtime.interwaly;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

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

    private int domyslnyCzasBiegu = 15;
    public void onAddBiegaj(View view) {
        EditText czasEditor = new EditText(this);
        czasEditor.setText(""+ domyslnyCzasBiegu);
        czasEditor.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        new AlertDialog.Builder(this)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int czas = parseInt(czasEditor.getText().toString());
                        domyslnyCzasBiegu = czas;
                        Bieg bieg = new Bieg(czas);
                        interwaly.add(bieg);
                        redrawIntervals();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .setView(czasEditor)
                .setMessage(getString(R.string.input_run_time))
                .show();
    }

    private int domyslnyCzasPrzerwy = 45;
    public void onAddPrzerwa(View view) {
        EditText czasEditor = new EditText(this);
        czasEditor.setText(""+ domyslnyCzasPrzerwy);
        czasEditor.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        new AlertDialog.Builder(this)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int czas = parseInt(czasEditor.getText().toString());
                        domyslnyCzasPrzerwy = czas;
                        Przerwa przerwa = new Przerwa(czas);
                        interwaly.add(przerwa);
                        redrawIntervals();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .setView(czasEditor)
                .setMessage(getString(R.string.input_break_time))
                .show();
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

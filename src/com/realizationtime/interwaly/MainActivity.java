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

    ListOfInterwaly interwaly = new ListOfInterwaly();

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
        for (Interwal interwal:interwaly.getList()) {
            Button nextButton = new Button(getApplicationContext());
            nextButton.setText(""+interwal.getCzas());
            nextButton.setOnClickListener(createInterwalButtonListener(interwal));
            if (interwal instanceof Bieg) {
                nextButton.setBackgroundColor(Color.RED);
            } else {
                //Przerwa
                nextButton.setBackgroundColor(Color.GREEN);
            }
            podInterwaly.addView(nextButton);
        }
    }

    private View.OnClickListener createInterwalButtonListener(Interwal interwal) {
        EditText czasEditor = new EditText(this);
        czasEditor.setText("" + interwal.getCzas());
        czasEditor.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity.this)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                interwaly.replace(interwal, parseInt(czasEditor.getText().toString()));
                                redrawIntervals();
                            }
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .setNeutralButton(getString(R.string.Remove), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                interwaly.remove(interwal);
                                redrawIntervals();
                            }
                        })
                        .setView(czasEditor)
                        .show();
            }
        };
    }

}

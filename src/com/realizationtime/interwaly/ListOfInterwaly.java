package com.realizationtime.interwaly;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.realizationtime.interwaly.Sprint.SPRINT_STARTING_LETTER;
import static java.lang.Integer.parseInt;

public class ListOfInterwaly {
    private static final String INTERWALY_KEY = "interwaly";

    public static ListOfInterwaly getFromPersistence(Activity activity){
        String interval_pers = activity.getPreferences(Context.MODE_PRIVATE).getString(INTERWALY_KEY, "");
        return getFromString(activity, interval_pers);
    }

    public static ListOfInterwaly getFromString(Activity activity, String intervalString) {
        ListOfInterwaly ret = new ListOfInterwaly(activity);
        if ("".equals(intervalString)){
            return ret;
        }
        String[] interwaly = intervalString.split(",");
        for (String next : interwaly) {
            try {
                if (next.startsWith(SPRINT_STARTING_LETTER)){
                    ret.list.add(new Sprint( parseInt(next.substring(1)) ));
                } else {
                    ret.list.add(new Przerwa( parseInt(next.substring(1)) ));
                }
            } catch (Exception e) {
                Log.e(activity.getLocalClassName(), "Error reading interval: " + next, e);
            }
        }
        return ret;
    }

    private ListOfInterwaly (Activity activity) {
        this.activity = activity;
    }
    private Activity activity;
    private List<Interwal> list = new ArrayList<>();

    public List<Interwal> getList () {
        return list;
    }

    public void add(Interwal interwal) {
        list.add(interwal);

        persist();
    }

    public void replace(Interwal interwal, int czas) {
        Interwal nowyInterwal = replaceTime(interwal, czas);
        int indexStarego = list.indexOf(interwal);
        list.remove(indexStarego);
        list.add(indexStarego, nowyInterwal);

        persist();
    }

    public void remove(Interwal interwal) {
        list.remove(interwal);

        persist();
    }

    public long getTotalTimeInMS(){
        long sum = 0;
        for (Interwal n : list) {
            sum += n.getCzas()*1000L;
        }
        return sum;
    }

    private void persist(){
        activity.getPreferences(Context.MODE_PRIVATE).edit()
                .putString(INTERWALY_KEY, toString()).commit();
    }

    @Override
    public String toString() {
        String ret = "";
        for (Interwal interwal : list) {
            ret+=interwal.toString()+",";
        }
        if (ret.length()>0) {
            ret = ret.substring(0, ret.length()-1);
        }
        return ret;
    }

    private static Interwal replaceTime(Interwal interwal, int czas) {
        if (interwal instanceof Przerwa) {
            return new Przerwa(czas);
        } else {
            return new Sprint(czas);
        }
    }
}

package com.realizationtime.interwaly.trening;

public class TimeFormatter {
    public static String formatWholeSeconds(int millis) {
        int seconds = millis / 1000;
        int minutes = seconds / 60;
        seconds %= 60;
        String ret = "";
        if (minutes > 0) {
            ret += minutes + ":";
            if (seconds < 10) {
                ret += "0" + seconds;
            } else {
                ret += seconds;
            }
        } else {
            ret += seconds;
        }
        return ret;
    }

    public static String formatTenthsOfSecond(int millis) {
        int tenths = (millis % 1000) / 100;
        return formatWholeSeconds(millis) + "." + tenths;
    }
}

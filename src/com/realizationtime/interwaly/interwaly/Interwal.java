package com.realizationtime.interwaly.interwaly;

public abstract class Interwal {

    private int czas;

    public Interwal(int czas) {
        this.czas = czas;
    }

    public int getCzas() {
        return czas;
    }

    public long getCzasMS() {
        return getCzas() * 1000L;
    }
}

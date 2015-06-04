package com.realizationtime.interwaly.interwaly;

public class Przerwa extends Interwal {
    public static final String PRZERWA_STARTING_LETTER = "P";
    public Przerwa(int czas) {
        super(czas);
    }

    @Override
    public String toString() {
        return PRZERWA_STARTING_LETTER+getCzas();
    }
}

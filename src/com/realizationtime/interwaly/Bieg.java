package com.realizationtime.interwaly;

public class Bieg extends Interwal {
    public static final String BIEG_STARTING_LETTER = "B";

    public Bieg(int czas) {
        super(czas);
    }

    @Override
    public String toString() {
        return BIEG_STARTING_LETTER+getCzas();
    }
}

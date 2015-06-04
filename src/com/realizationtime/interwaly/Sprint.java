package com.realizationtime.interwaly;

public class Sprint extends Interwal {
    public static final String SPRINT_STARTING_LETTER = "B";

    public Sprint(int czas) {
        super(czas);
    }

    @Override
    public String toString() {
        return SPRINT_STARTING_LETTER +getCzas();
    }
}

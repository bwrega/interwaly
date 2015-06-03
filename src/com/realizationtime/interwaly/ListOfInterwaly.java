package com.realizationtime.interwaly;

import java.util.ArrayList;
import java.util.List;

public class ListOfInterwaly {
    private List<Interwal> list = new ArrayList<>();

    public void add(Interwal interwal) {
        list.add(interwal);
    }

    public List<Interwal> getList () {
        return list;
    }

    public void replace(Interwal interwal, int czas) {
        Interwal nowyInterwal = replaceTime(interwal, czas);
        int indexStarego = list.indexOf(interwal);
        list.remove(indexStarego);
        list.add(indexStarego, nowyInterwal);
    }

    private static Interwal replaceTime(Interwal interwal, int czas) {
        if (interwal instanceof Przerwa) {
            return new Przerwa(czas);
        } else {
            return new Bieg(czas);
        }
    }

    public void remove(Interwal interwal) {
        list.remove(interwal);
    }
}

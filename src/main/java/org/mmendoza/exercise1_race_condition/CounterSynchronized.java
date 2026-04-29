package org.mmendoza.exercise1_race_condition;

public class CounterSynchronized {

    private int counter = 0;

    public int getCounter() {
        return counter;
    }

    public synchronized void increment() {
        counter++;
    }
}

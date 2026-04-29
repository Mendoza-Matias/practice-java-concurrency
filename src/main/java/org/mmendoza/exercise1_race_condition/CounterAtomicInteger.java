package org.mmendoza.exercise1_race_condition;

import java.util.concurrent.atomic.AtomicInteger;

public class CounterAtomicInteger {

    private AtomicInteger counter = new AtomicInteger(0);

    public AtomicInteger getCounter() {
        return counter;
    }

    public void increment() {
        counter.incrementAndGet();
    }
}

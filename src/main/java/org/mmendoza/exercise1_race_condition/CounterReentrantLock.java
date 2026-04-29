package org.mmendoza.exercise1_race_condition;

import java.util.concurrent.locks.ReentrantLock;

public class CounterReentrantLock {

    private final ReentrantLock lock = new ReentrantLock();

    private int counter = 0;

    public int getCounter() {
        return counter;
    }

    public void increment() {
        lock.lock();
        try {
            counter++;
        } finally {
            lock.unlock();
        }
    }

}

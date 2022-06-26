package com.android.threadsapp;

public class Counter {

    private long counter = 0;

    synchronized void inc() {
        counter++;
    }

    long getCounter() {
        return counter;
    }

    synchronized void reset() {
        counter = 0;
    }

}

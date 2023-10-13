package org.example.task6;

interface Producer extends Runnable {
    void stop();
    long getOperations();
    long getRuntime();
}

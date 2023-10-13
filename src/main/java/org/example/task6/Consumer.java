package org.example.task6;

public interface Consumer extends Runnable{

    void stop();

    long getOperations();
}

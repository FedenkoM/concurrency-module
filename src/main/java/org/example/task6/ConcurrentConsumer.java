package org.example.task6;

import org.fusesource.jansi.Ansi;

import java.util.concurrent.BlockingQueue;

import static org.example.utils.StringMassageUtils.customizeString;

public class ConcurrentConsumer implements Consumer {
    private final BlockingQueue<Integer> queue;
    private volatile boolean running = true;
    private long operations = 0;

    public ConcurrentConsumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (running) {
            try {
                int num = queue.take();
                operations++;
                System.out.println(customizeString("Concurrent consumer: ", Ansi.Color.CYAN) + num);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public long getOperations() {
        return operations;
    }
}

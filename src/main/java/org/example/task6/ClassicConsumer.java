package org.example.task6;

import org.fusesource.jansi.Ansi;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;

import static org.example.utils.StringMassageUtils.customizeString;

class ClassicConsumer implements Consumer {
    private final Queue<Integer> queue;
    private volatile boolean running = true;
    private long operations = 0;

    public ClassicConsumer(Queue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (running) {
            int num;
            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                num = queue.poll();
                operations++;
            }

            System.out.println(customizeString("Classic consumer: ", Ansi.Color.YELLOW) + num);
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
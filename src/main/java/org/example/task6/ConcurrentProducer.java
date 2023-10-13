package org.example.task6;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

class ConcurrentProducer implements Producer {
    private final BlockingQueue<Integer> queue;
    private volatile boolean running = true;
    private long operations = 0;
    private long startTime;

    public ConcurrentProducer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        startTime = System.currentTimeMillis();
        Random random = new Random();

        while (running) {
            try {
                if (queue.size() < ProducerConsumerApplication.CAPACITY) {
                    int num = random.nextInt(100);
                    queue.put(num);
                    operations++;
                }
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

    @Override
    public long getRuntime() {
        return System.currentTimeMillis() - startTime;
    }
}
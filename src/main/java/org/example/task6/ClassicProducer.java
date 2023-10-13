package org.example.task6;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.example.task6.ProducerConsumerApplication.CAPACITY;

public class ClassicProducer implements Producer {
    private final Queue<Integer> queue;
    private volatile boolean running = true;
    private long operations = 0;
    private long startTime;

    public ClassicProducer(Queue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        startTime = System.currentTimeMillis();
        Random random = ThreadLocalRandom.current();

        while (running) {
            synchronized (queue) {
                if (queue.size() < CAPACITY) {
                    int num = random.nextInt(100);
                    queue.offer(num);
                    operations++;
                }
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
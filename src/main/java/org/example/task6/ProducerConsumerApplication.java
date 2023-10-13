package org.example.task6;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerApplication {
    public static final int CAPACITY = 1000;

    public static void main(String[] args) {
        System.out.println("Classic Model:");
        classicModel();

        System.out.println("\nConcurrency Model:");
        concurrencyModel();
    }

    private static void classicModel() {
        Queue<Integer> classicQueue = new LinkedList<>();
        var producer = new ClassicProducer(classicQueue);
        var consumer = new ClassicConsumer(classicQueue);

        runProducerConsumer(producer, consumer);
    }

    private static void concurrencyModel() {
        BlockingQueue<Integer> concurrentQueue = new ArrayBlockingQueue<>(CAPACITY);
        ConcurrentProducer producer = new ConcurrentProducer(concurrentQueue);
        ConcurrentConsumer consumer = new ConcurrentConsumer(concurrentQueue);

        runProducerConsumer(producer, consumer);
    }

    private static void runProducerConsumer(Producer producer, Consumer consumer) {
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            producer.stop();
            consumer.stop();

            try {
                producerThread.join();
                consumerThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            long opsPerSecond = producer.getOperations() / producer.getRuntime();
            System.out.println("Operations per second: " + opsPerSecond);
        }));
    }
}






package org.example.task1;

import org.fusesource.jansi.Ansi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.example.utils.StringMassageUtils.customizeString;

public class Application1 {

    public static void main(String[] args) {
        int numThreads = 2;
        int numElements = 1000;
        var hashMap = new HashMap<Integer, Integer>();
        var synchronizedMap = Collections.synchronizedMap(new HashMap<Integer, Integer>());
        var concurrentHashMap = new ConcurrentHashMap<Integer, Integer>();

        runTest(customizeString("HashMap", Ansi.Color.CYAN), hashMap, numThreads, numElements); // Can throw ConcurrentModificationException
        runTest(customizeString("SynchronizedMap", Ansi.Color.GREEN), synchronizedMap, numThreads, numElements); // Can throw ConcurrentModificationException
        runTest(customizeString("ConcurrentHashMap", Ansi.Color.MAGENTA), concurrentHashMap, numThreads, numElements);
    }

    private static void runTest(String mapType, Map<Integer, Integer> map, int numThreads, int numElements) {
        var sum = new AtomicInteger(0);

        long startTime = System.nanoTime();
        var threads = new Thread[numThreads];

        processMap(map, numThreads, numElements, sum, threads);
        startThreads(threads);
        joinThreads(threads);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; // milliseconds

        System.out.println("[%s] Test".formatted(mapType));
        System.out.println("Sum: [%s]".formatted(sum.get()));
        System.out.println("Execution Time: [%s] ms\n".formatted(duration));
    }

    private static void processMap(Map<Integer, Integer> map, int numThreads, int numElements, AtomicInteger sum, Thread[] threads) {
        for (int i = 0; i < numThreads; i++) {
            if (i == 0) {
                threads[i] = putElementIntoMapInNewThread(map, numElements);
            } else {
                threads[i] = sumMapElementsInNewThread(map, numElements, sum);
            }
        }
    }

    private static void joinThreads(Thread[] threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void startThreads(Thread[] threads) {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    private static Thread sumMapElementsInNewThread(Map<Integer, Integer> map, int numElements, AtomicInteger sum) {
        return new Thread(() -> {
            for (int j = 0; j < numElements; j++) {
                for (int value : map.values()) {
                    sum.addAndGet(value);
                }
            }
        });
    }

    private static Thread putElementIntoMapInNewThread(Map<Integer, Integer> map, int numElements) {
        return new Thread(() -> {
            for (int j = 0; j < numElements; j++) {
                map.put(j, j);
            }
        });
    }
}

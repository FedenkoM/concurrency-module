package org.example.task4;

import org.fusesource.jansi.Ansi;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.example.utils.StringMassageUtils.customizeString;

public class BlockingObjectPool {
    private final BlockingQueue<Object> pool;

    public BlockingObjectPool(int size) {
        pool = new ArrayBlockingQueue<>(size);
        initializePool(size);
    }

    private void initializePool(int size) {
        for (int i = 0; i < size; i++) {
            pool.offer(createObject());
        }
    }

    public Object get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public void take(Object object) {
        try {
            pool.put(object);
            System.out.println(customizeString("PUT Object [%s] to pool!".formatted(object), Ansi.Color.MAGENTA));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private Object createObject() {
        return new Object();
    }

    public int getSize() {
        return pool.size();
    }
}

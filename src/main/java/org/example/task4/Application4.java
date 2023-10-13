package org.example.task4;

public class Application4 {
    public static void main(String[] args) {
        BlockingObjectPool blockingObjectPool = new BlockingObjectPool(3);
        System.out.println("Size: " + blockingObjectPool.getSize());
        System.out.println("Get object from pool: " + blockingObjectPool.get());
        blockingObjectPool.take("1");
        System.out.println("Get object from pool: " + blockingObjectPool.get());
        blockingObjectPool.take("2");
        System.out.println("Get object from pool: " + blockingObjectPool.get());
        blockingObjectPool.take("3");
        System.out.println("Get object from pool: " + blockingObjectPool.get());
        blockingObjectPool.take("4");
        blockingObjectPool.take("5"); // will wait as queue is full
    }
}

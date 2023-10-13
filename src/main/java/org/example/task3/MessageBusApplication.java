package org.example.task3;

public class MessageBusApplication {
    public static void main(String[] args) {
        MessageBus messageBus = new MessageBus();
        String[] topics = { "topic1", "topic2", "topic3" };

        // Create producers and consumers
        var producer1 = new Thread(new Producer(messageBus, topics));
        var producer2 = new Thread(new Producer(messageBus, topics));
        var consumer1 = new Thread(new Consumer(messageBus, "topic3"));
        var consumer2 = new Thread(new Consumer(messageBus, "topic2"));

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
    }
}

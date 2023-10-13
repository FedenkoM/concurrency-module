package org.example.task3;

import java.util.concurrent.ThreadLocalRandom;

public class Producer implements Runnable {
    private MessageBus messageBus;
    private String[] topics;

    public Producer(MessageBus messageBus, String[] topics) {
        this.messageBus = messageBus;
        this.topics = topics;
    }

    @Override
    public void run() {
        var random = ThreadLocalRandom.current();
        while (true) {
            String topic = topics[random.nextInt(topics.length)];
            String payload = "Message from " + Thread.currentThread().getName();
            messageBus.publish(new Message(topic, payload));
        }
    }
}
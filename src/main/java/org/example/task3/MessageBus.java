package org.example.task3;

import java.util.ArrayList;
import java.util.List;

class MessageBus {
    private List<Message> messageQueue = new ArrayList<>();

    public void publish(Message message) {
        synchronized (messageQueue) {
            messageQueue.add(message);
            messageQueue.notify(); // Notify consumers
        }
    }

    public Message consume(String topic) {
        synchronized (messageQueue) {
            while (messageQueue.isEmpty()) {
                try {
                    messageQueue.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            for (Message message : messageQueue) {
                if (message.getTopic().equals(topic)) {
                    messageQueue.remove(message);
                    return message;
                }
            }
            return null;
        }
    }
}
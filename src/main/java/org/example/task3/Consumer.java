package org.example.task3;

import org.fusesource.jansi.Ansi;

import static org.example.utils.StringMassageUtils.customizeString;

public class Consumer implements Runnable {
    private MessageBus messageBus;
    private String topic;

    public Consumer(MessageBus messageBus, String topic) {
        this.messageBus = messageBus;
        this.topic = topic;
    }

    @Override
    public void run() {
        while (true) {
            var message = messageBus.consume(topic);
            if (message != null) {
                System.out.println(
                    customizeString("Consumer for topic " +
                                    customizeString(topic, Ansi.Color.CYAN) +
                                    " received: ", Ansi.Color.GREEN) +
                    message.getPayload());
            }
        }
    }
}
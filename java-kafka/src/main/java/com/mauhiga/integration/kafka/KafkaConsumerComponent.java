package com.mauhiga.integration.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerComponent {


    @Value(value = "${message.topic.name}")
    private String topicName;

    public KafkaConsumerComponent() {

    }

    class KafkaConsumerRunner implements Runnable {
        private final AtomicBoolean closed = new AtomicBoolean(false);
        private final KafkaConsumer<String, String> consumer;

        public KafkaConsumerRunner(KafkaConsumer<String, String> consumer) {
            this.consumer = consumer;
        }

        public void run() {
            try {
                consumer.subscribe(Arrays.asList("test"));
                while (!closed.get()) {
                    ConsumerRecords<String, String> records = consumer
                            .poll(Duration.ofMillis(10000));
                    // Handle new records
                }
            } catch (WakeupException e) {
                // Ignore exception if closing
                if (!closed.get())
                    throw e;
            } finally {
                consumer.close();
            }
        }

        // Shutdown hook which can be called from a separate thread
        public void shutdown() {
            closed.set(true);
            consumer.wakeup();
        }
    }
    @PostConstruct
    public final void initConsumer() {

    }
}

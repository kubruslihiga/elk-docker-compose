package com.mauhiga.integration.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "kafka")
public class KafkaWS {

    @PostMapping
    public ResponseEntity<String> produce() {
        String response = "{ \"response\" : \"ok\" }";
        Map<String, Object> conf = new HashMap<>();
        conf.put("bootstrap.servers", "10.5.0.3:9092");
        conf.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        conf.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        try (Producer<String, String> producer = new KafkaProducer<>(conf);) {
            ProducerRecord<String, String> record = new ProducerRecord<>("test", "{ \"teste\" : \"teste via java\"}"); 
            producer.send(record);
        }
        return ResponseEntity.ok().body(response);
    }
    
    public static void main(String[] args) {
        KafkaWS ws = new KafkaWS();
        System.out.println(ws.produce());
    }
}

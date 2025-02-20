package kr.hhplus.be.server.interfaces.kafka_connection_test;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMassage(String topic, Object message){
        kafkaTemplate.send(topic,message);
    }
}

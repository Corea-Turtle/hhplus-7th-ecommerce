package kr.hhplus.be.server.interfaces.kafka_connection_test;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;


@Service
public class KafkaConsumerTest {
    private CountDownLatch latch = new CountDownLatch(1); //1초에 한번
    private final Queue<String> receivedMessageQueue = new LinkedList<>();

    @KafkaListener(topics = "test-topic", groupId = "demo")
    public void listen(ConsumerRecord<?,?> consumerRecord){
        String message = consumerRecord.value().toString();

        receivedMessageQueue.offer(message);
        System.out.println("Received message: " + message);
        latch.countDown();
    }

    public Queue<String> getReceivedMessages() throws InterruptedException{
        latch.await();
        return receivedMessageQueue;
    }

}

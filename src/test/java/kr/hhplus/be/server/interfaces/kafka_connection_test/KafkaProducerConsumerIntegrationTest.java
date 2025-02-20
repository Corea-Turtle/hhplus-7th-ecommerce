package kr.hhplus.be.server.interfaces.kafka_connection_test;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;


@SpringBootTest
@Testcontainers
@Transactional
class KafkaProducerConsumerIntegrationTest {

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    KafkaConsumerTest kafkaConsumer;


@DisplayName("[성공]KafkaProducer(Topic)에서 전송한 메시지(String)를 KafkaConsumer에서 받아 출력한다.")
@Test
void getMessageReceivedToConsumerFromProducerList() throws InterruptedException {
    //given
    String topic = "test-topic";
    List<String> messages = List.of("1번째 메시지", "2번째 메시지", "3번째 메시지", "4번째 메시지");

    //when
    for (String message : messages) {
        kafkaProducer.sendMassage(topic, message);
    }

    // then
    Awaitility.await()
            .pollInterval(Duration.ofMillis(300)) // 300ms마다 체크
            .atMost(4, TimeUnit.SECONDS) // 최대 4초 대기
            .untilAsserted(() -> {

                Queue<String> queue = kafkaConsumer.getReceivedMessages();

                List<String> receivedMessages = new ArrayList<>();
                for (String msg : queue) {
                    receivedMessages.add(msg.replaceAll("^\"|\"$", ""));
                }

                Assertions.assertThat(receivedMessages)
                        .containsExactlyElementsOf(messages);
            });
    }
}

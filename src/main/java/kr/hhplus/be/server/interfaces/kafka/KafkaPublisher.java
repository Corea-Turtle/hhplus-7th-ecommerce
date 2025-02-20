package kr.hhplus.be.server.interfaces.kafka;

import kr.hhplus.be.server.outbox.payment.PaymentCreatedEvent;
import kr.hhplus.be.server.outbox.payment.PaymentCreatedMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class KafkaPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishPaymentInfo(PaymentCreatedMessage message){
        String topic = "payment_create_info";
        kafkaTemplate.send(topic,message);
    }

    public void publishPaymentConfirmInfo(PaymentCreatedMessage message){
        String topic = "payment_confirm_info";
        kafkaTemplate.send(topic,message);
    }
}

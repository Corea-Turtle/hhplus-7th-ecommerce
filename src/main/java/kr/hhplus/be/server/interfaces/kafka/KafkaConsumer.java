package kr.hhplus.be.server.interfaces.kafka;

import kr.hhplus.be.server.domain.purchase_order.PurchaseOrderState;
import kr.hhplus.be.server.infrastructure.outbox.payment.PaymentCreatedEventRepositoryImpl;
import kr.hhplus.be.server.outbox.payment.PaymentCreatedEvent;
import kr.hhplus.be.server.outbox.payment.PaymentCreatedMessage;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;


@RequiredArgsConstructor
@Service
public class KafkaConsumer {

    private final PaymentCreatedEventRepositoryImpl paymentCreatedEventRepository;
    private final KafkaPublisher kafkaPublisher;


    private CountDownLatch latch = new CountDownLatch(1); //3초에 한번
    private PaymentCreatedMessage receivedMessage;


    @KafkaListener(topics = "payment_create_info", groupId = "demo")
    public void listenCreate(ConsumerRecord<?,?> consumerRecord){
        this.receivedMessage = (PaymentCreatedMessage) consumerRecord.value();
        selfConsume(receivedMessage);
        latch.countDown();
    }

    @KafkaListener(topics = "payment_confirm_info", groupId = "demo")
    public void listenConfirm(ConsumerRecord<?,?> consumerRecord){
        this.receivedMessage = (PaymentCreatedMessage) consumerRecord.value();
        consume(receivedMessage);
        latch.countDown();
    }

    // outbox 수정
    public void selfConsume(PaymentCreatedMessage paymentCreatedMessage) {
        PaymentCreatedEvent paymentCreatedEvent =  paymentCreatedEventRepository.findById(paymentCreatedMessage.getPaymentEventId())
                .orElseThrow(()->new IllegalArgumentException("결제 이벤트를 못 찾았다."));

        if(paymentCreatedEvent.getState().equals(PurchaseOrderState.PAYMENT_INIT)){
            paymentCreatedEvent.updateState(PurchaseOrderState.PAYMENT_COMPLETE);
        }else{
            kafkaPublisher.publishPaymentConfirmInfo(paymentCreatedMessage);
        }
        paymentCreatedEventRepository.save(paymentCreatedEvent);
    }

    // outbox 통과
    public void consume(PaymentCreatedMessage paymentCreatedMessage) {
        PaymentCreatedEvent paymentCreatedEvent =  paymentCreatedEventRepository.findById(paymentCreatedMessage.getPaymentEventId())
                .orElseThrow(()->new IllegalArgumentException("결제 이벤트를 못 찾았다."));

        paymentCreatedEventRepository.save(paymentCreatedEvent);
    }

}

package kr.hhplus.be.server.outbox.payment;

import kr.hhplus.be.server.domain.payment.event.PaymentEvent;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrderState;
import kr.hhplus.be.server.interfaces.kafka.KafkaPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;
import static org.springframework.transaction.event.TransactionPhase.BEFORE_COMMIT;


@RequiredArgsConstructor
public class PaymentEventListener {

    private final PaymentCreatedEventRepository paymentCreatedEventRepository;

    private final KafkaPublisher kafkaPublisher;

    // outbox 저장
    @TransactionalEventListener(phase = BEFORE_COMMIT)
    public void saveOutbox(PaymentEvent paymentEvent) {

        PaymentCreatedEvent paymentCreatedEvent = new PaymentCreatedEvent(paymentEvent.getPaymentId(),paymentEvent.getPurchaseOrderId(),paymentEvent.getState());

        paymentCreatedEventRepository.save(paymentCreatedEvent);
    }

    // 메시지 발행
    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void sendPaymentInfo(PaymentCreatedEvent paymentCreatedEvent) {

        PaymentCreatedMessage paymentCreatedMessage = new PaymentCreatedMessage(paymentCreatedEvent.getId(),paymentCreatedEvent.getPaymentId(),paymentCreatedEvent.getPurchaseOrderId(),paymentCreatedEvent.getState());

        kafkaPublisher.publishPaymentInfo(paymentCreatedMessage);
    }
}

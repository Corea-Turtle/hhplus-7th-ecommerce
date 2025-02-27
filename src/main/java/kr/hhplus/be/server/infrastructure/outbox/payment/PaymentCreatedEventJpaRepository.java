package kr.hhplus.be.server.infrastructure.outbox.payment;

import kr.hhplus.be.server.outbox.payment.PaymentCreatedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentCreatedEventJpaRepository extends JpaRepository<PaymentCreatedEvent, Long> {
}

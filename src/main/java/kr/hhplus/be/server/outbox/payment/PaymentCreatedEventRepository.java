package kr.hhplus.be.server.outbox.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentCreatedEventRepository{
    void save(PaymentCreatedEvent paymentCreatedEvent);
    Optional<PaymentCreatedEvent> findById(Long id);
}

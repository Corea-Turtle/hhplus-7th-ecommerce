package kr.hhplus.be.server.infrastructure.outbox.payment;

import kr.hhplus.be.server.outbox.payment.PaymentCreatedEvent;
import kr.hhplus.be.server.outbox.payment.PaymentCreatedEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@RequiredArgsConstructor
@Repository
public class PaymentCreatedEventRepositoryImpl implements PaymentCreatedEventRepository {

    private final PaymentCreatedEventJpaRepository paymentCreatedEventJpaRepository;

    @Override
    public void save(PaymentCreatedEvent paymentCreatedEvent) {
        paymentCreatedEventJpaRepository.save(paymentCreatedEvent);
    }

    @Override
    public Optional<PaymentCreatedEvent> findById(Long id) {
        return paymentCreatedEventJpaRepository.findById(id);
    }
}

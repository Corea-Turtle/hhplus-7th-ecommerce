package kr.hhplus.be.server.infrastructure.payment;

import kr.hhplus.be.server.domain.payment.Payment;
import kr.hhplus.be.server.domain.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Optional<Payment> findById(Long id) {
        return paymentJpaRepository.findById(id);
    }

    @Override
    public Optional<Payment> findTopByUserIdAndPurchaseOrderIdOrderByIdDesc(Long userId, Long purchaseOrderId) {
        return paymentJpaRepository.findTopByUserIdAndPurchaseOrderIdOrderByIdDesc(userId,purchaseOrderId);
    }

    @Override
    public List<Payment> findAll() {
        return paymentJpaRepository.findAll();
    }

    @Override
    public void save(Payment payment) {
        paymentJpaRepository.save(payment);
    }
}

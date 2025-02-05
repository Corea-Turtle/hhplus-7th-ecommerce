package kr.hhplus.be.server.domain.payment;

import kr.hhplus.be.server.domain.coupon.Coupon;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {
    Optional<Payment> findById(Long id);
    List<Payment> findAll();
    void save(Payment payment);
}

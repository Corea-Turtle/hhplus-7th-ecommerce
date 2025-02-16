package kr.hhplus.be.server.domain.payment;

import kr.hhplus.be.server.domain.coupon.Coupon;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {
    Optional<Payment> findById(Long id);
    Optional<Payment> findTopByUserIdAndPurchaseOrderIdOrderByIdDesc(Long userId, Long PurchaseOrderId);
    List<Payment> findAll();
    void save(Payment payment);
}

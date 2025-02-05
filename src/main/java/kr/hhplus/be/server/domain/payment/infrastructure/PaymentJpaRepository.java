package kr.hhplus.be.server.domain.payment.infrastructure;

import kr.hhplus.be.server.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment,Long> {


}

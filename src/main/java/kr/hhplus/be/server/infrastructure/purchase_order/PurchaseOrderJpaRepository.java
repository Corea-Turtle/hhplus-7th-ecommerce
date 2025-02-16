package kr.hhplus.be.server.infrastructure.purchase_order;

import kr.hhplus.be.server.domain.purchase_order.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderJpaRepository extends JpaRepository<PurchaseOrder,Long> {
}

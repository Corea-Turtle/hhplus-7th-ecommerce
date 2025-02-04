package kr.hhplus.be.server.domain.purchase_order.infrastructure;

import kr.hhplus.be.server.domain.purchase_order.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderJpaRepository extends JpaRepository<PurchaseOrder,Long> {
}

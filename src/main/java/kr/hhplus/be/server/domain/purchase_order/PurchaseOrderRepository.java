package kr.hhplus.be.server.domain.purchase_order;

import kr.hhplus.be.server.domain.coupon.Coupon;

import java.util.List;
import java.util.Optional;

public interface PurchaseOrderRepository {
    Optional<PurchaseOrder> findById(Long id);
    List<PurchaseOrder> findAll();
    void save(PurchaseOrder purchaseOrder);
}

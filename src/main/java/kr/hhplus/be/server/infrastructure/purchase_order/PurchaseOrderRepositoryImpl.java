package kr.hhplus.be.server.infrastructure.purchase_order;

import kr.hhplus.be.server.domain.purchase_order.PurchaseOrder;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PurchaseOrderRepositoryImpl implements PurchaseOrderRepository {

    private final PurchaseOrderJpaRepository purchaseOrderJpaRepository;


    @Override
    public Optional<PurchaseOrder> findById(Long id) {
        return purchaseOrderJpaRepository.findById(id);
    }

    @Override
    public List<PurchaseOrder> findAll() {
        return purchaseOrderJpaRepository.findAll();
    }

    @Override
    public void save(PurchaseOrder purchaseOrder) {
        purchaseOrderJpaRepository.save(purchaseOrder);
    }
}

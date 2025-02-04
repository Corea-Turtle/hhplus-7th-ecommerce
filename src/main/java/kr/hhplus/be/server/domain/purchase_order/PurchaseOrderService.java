package kr.hhplus.be.server.domain.purchase_order;

import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.purchase_order.infrastructure.PurchaseOrderRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepositoryImpl purchaseOrderRepository;

    //주문 생성
    public void createPurchaseOrder(PurchaseOrder purchaseOrder){
        purchaseOrderRepository.save(purchaseOrder);
    }
    //주문 삭제

}

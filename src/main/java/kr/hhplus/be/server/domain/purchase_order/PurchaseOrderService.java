package kr.hhplus.be.server.domain.purchase_order;

import kr.hhplus.be.server.infrastructure.purchase_order.PurchaseOrderRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepositoryImpl purchaseOrderRepository;

    //주문 있나 확인
    public PurchaseOrder getPurchaseOrderExist(Long purchaseOrderId){
        //주문 자체가 없을떄
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(()->new IllegalArgumentException("주문 아이디에 해당하는 주문이 없습니다."));
        //주문은 존재하나 상태가 주문생성(order_pending) 상태가 아니면 유효한 주문이 아님
        if(purchaseOrder.getState() != PurchaseOrderState.ORDER_PENDING){
            throw new IllegalArgumentException("진행 가능한 주문이 없습니다.");
        }
        return purchaseOrder;
    }

    //주문 생성
    public void createPurchaseOrder(PurchaseOrder purchaseOrder) {
        getPurchaseOrderExist(purchaseOrder.getId());
        purchaseOrderRepository.save(purchaseOrder);
    }



    //주문 상태 변경
    public void purchaseOrderUpdateRequest(PurchaseOrder purchaseOrder, PurchaseOrderState state){
        purchaseOrder.updatePurchaseOrder(state);
        purchaseOrderRepository.save(purchaseOrder);
    }

    //주문 삭제(나중에)

}

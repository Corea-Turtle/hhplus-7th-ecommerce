package kr.hhplus.be.server.interfaces.api.purchase_order.controller;


import kr.hhplus.be.server.application.purchase_order.OrderCreateFacade;
import kr.hhplus.be.server.interfaces.api.purchase_order.dto.request.PurchaseOrderCreateRequest;
import kr.hhplus.be.server.interfaces.api.purchase_order.dto.response.PurchaseOrderCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/purchase_order")
public class PurchaseOrderController {

    private final OrderCreateFacade orderCreateFacade;

    //주문 생성
    public ResponseEntity<PurchaseOrderCreateResponse> createPurchaseOrder(@RequestBody PurchaseOrderCreateRequest request){
        PurchaseOrderCreateResponse response = orderCreateFacade.createOrder(request); //주문 생성 파사드에서
        return ResponseEntity.ok()
                .body(response);
    }



}

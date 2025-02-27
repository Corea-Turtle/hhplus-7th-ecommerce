package kr.hhplus.be.server.application.payment;

import kr.hhplus.be.server.domain.payment.PaymentService;
import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrder;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrderService;
import kr.hhplus.be.server.domain.statics.StaticsService;
import kr.hhplus.be.server.domain.stock.StockService;
import kr.hhplus.be.server.domain.user.User;
import kr.hhplus.be.server.domain.user.UserService;
import kr.hhplus.be.server.interfaces.api.payment.dto.request.PaymentConfirmRequest;
import kr.hhplus.be.server.interfaces.api.payment.dto.response.PaymentConfirmResponse;
import kr.hhplus.be.server.interfaces.api.user.dto.request.UserMyBalanceRequest;
import kr.hhplus.be.server.interfaces.api.user.dto.request.UserUpdateBalanceRequest;
import kr.hhplus.be.server.interfaces.api.user.dto.response.UserMyBalanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class PaymentConfirmFacade {

    private final UserService userService;

    private final PurchaseOrderService purchaseOrderService;

    private final PaymentService paymentService;

    private final StaticsService staticsService;

    private final StockService stockService;

    public PaymentConfirmResponse confirmPayment(PaymentConfirmRequest request){

        //유저가 있는지 검증
        User userExist = userService.getExistUser(request.getUserId());

        //주문이 있는지 검증
        PurchaseOrder purchaseOrderExist = purchaseOrderService.getPurchaseOrderExist(request.getPurchaseOrderId());

        //주문에 해당하는 유저의 잔액이 올바른지 확인 후 업데이트
        userService.updateUserBalanceGreaterThanOrEqualToTotalOrderPrice(userExist,purchaseOrderExist);


//        //TODO 주문의 상품 재고가 충분한지 검증
//        List<Product> products = purchaseOrderExist.getProducts();
//        for(Product product : products){
//            int stockRemain = stockService.getStockRemainQuantity(product.getId());
//            if(stockRemain>0){
//
//            }
//        }

        PaymentConfirmResponse response = new PaymentConfirmResponse(purchaseOrderExist.getId(), userExist.getId(), purchaseOrderExist.getState(), request.getVendor(), purchaseOrderExist.getTotalPrice());

        //결제 정보 저장(Transactional)
        paymentService.pay(userExist, purchaseOrderExist, response);

        //판매 상위 상품에 데이터 추가
        for(Product product: purchaseOrderExist.getProducts()){
            staticsService.insertStatics(product.getId(), product.getName(), product.getOrderAmount());
        }

        return  response;
    }
}

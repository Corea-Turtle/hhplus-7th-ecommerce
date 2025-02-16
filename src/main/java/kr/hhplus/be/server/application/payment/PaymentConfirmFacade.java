package kr.hhplus.be.server.application.payment;

import kr.hhplus.be.server.domain.payment.PaymentService;
import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrder;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrderService;
import kr.hhplus.be.server.domain.statics.StaticsService;
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

    public PaymentConfirmResponse confirmPayment(PaymentConfirmRequest request){

        //유저가 있는지 검증
        User userExist = userService.getExistUser(request.getUserId());

        //주문이 있는지 검증
        PurchaseOrder purchaseOrderExist = purchaseOrderService.getPurchaseOrderExist(request.getPurchaseOrderId());

        //주문에 해당하는 유저의 잔액이 올바른지 확인
        UserMyBalanceRequest balanceRequest = new UserMyBalanceRequest(request.getUserId());
        UserMyBalanceResponse balanceResponse = userService.getCurrentUserBalance(balanceRequest);

        if(balanceResponse.getBalance() >= purchaseOrderExist.getTotalPrice()){
            //To do
            //state 변경이 들어가야할까? no 로그만 기록하자
            UserUpdateBalanceRequest updateBalanceRequest = new UserUpdateBalanceRequest(userExist.getId(),-purchaseOrderExist.getTotalPrice());

            userService.updateUserBalance(updateBalanceRequest);

        }else{
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }

        //TODO주문의 재고가 충분한지 검증


        PaymentConfirmResponse response = new PaymentConfirmResponse(purchaseOrderExist.getId(), userExist.getId(), purchaseOrderExist.getState(), request.getVendor(), purchaseOrderExist.getTotalPrice());

        //결제 정보 저장
        paymentService.savePayment(response);

        //판매 상위 상품에 데이터 추가
        for(Product product: purchaseOrderExist.getProducts()){
            staticsService.insertStatics(product.getId(), product.getName(), product.getOrderAmount());
        }

        return  response;
    }
}

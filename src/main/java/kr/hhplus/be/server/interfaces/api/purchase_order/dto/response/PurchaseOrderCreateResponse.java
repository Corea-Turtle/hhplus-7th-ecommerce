package kr.hhplus.be.server.interfaces.api.purchase_order.dto.response;


import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrderState;
import kr.hhplus.be.server.domain.user_coupon.UserCoupon;
import kr.hhplus.be.server.interfaces.api.product.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PurchaseOrderCreateResponse {
    Long userId; //주문자ㅓ 아이디
    List<ProductDTO> orderProducts; //주문 상품 리스트(상품id, 이름, 주문 수량 등을 담고 있음);
    List<UserCoupon> usedCoupons;
    long totalPrice;
    String state;

    public PurchaseOrderCreateResponse(Long userId, List<ProductDTO> orderProducts, List<UserCoupon> usedCoupons, long totalPrice) {
        this.userId = userId;
        this.orderProducts = orderProducts;
        this.usedCoupons = usedCoupons;
        this.totalPrice = totalPrice;
        this.state =  PurchaseOrderState.ORDER_PENDING.getText();
    }
}

package kr.hhplus.be.server.interfaces.api.purchase_order.dto.response;


import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.user_coupon.UserCoupon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class PurchaseOrderCreateResponse {
    Long userId; //주문자ㅓ 아이디
    List<Product> orderProducts; //주문 상품 리스트(상품id, 이름, 주문 수량 등을 담고 있음);
    List<UserCoupon> usedCoupons;
    long totalPrice;
    String state; //주문 대기, 주문 완료, 주문 취소 등 - enum으로 할까?
}

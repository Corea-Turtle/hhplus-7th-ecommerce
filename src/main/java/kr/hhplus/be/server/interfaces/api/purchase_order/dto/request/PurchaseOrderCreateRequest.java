package kr.hhplus.be.server.interfaces.api.purchase_order.dto.request;

import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.user_coupon.UserCoupon;
import lombok.Getter;

import java.util.List;


@Getter
public class PurchaseOrderCreateRequest {
    Long userId;
    List<Product> orderProducts;
    List<UserCoupon> usedCoupons;
}

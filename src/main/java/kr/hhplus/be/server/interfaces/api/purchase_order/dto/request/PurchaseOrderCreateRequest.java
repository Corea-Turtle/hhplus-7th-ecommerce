package kr.hhplus.be.server.interfaces.api.purchase_order.dto.request;

import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.user_coupon.UserCoupon;
import kr.hhplus.be.server.interfaces.api.product.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PurchaseOrderCreateRequest {
    Long userId;
    List<ProductDTO> orderProducts;
    List<UserCoupon> usedCoupons;
}

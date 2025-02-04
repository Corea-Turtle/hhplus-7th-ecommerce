package kr.hhplus.be.server.domain.purchase_order;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.user_coupon.UserCoupon;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="purchase_order")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private List<Product> products; // 주문 수량이 포함된 상품 리스트

    private List<UserCoupon> userUsedCoupons;  // 사용자가 사용한 쿠폰들

    private PurchaseOrderState state;

    public PurchaseOrder(Long userId, List<Product> products, List<UserCoupon> userUsedCoupons, PurchaseOrderState state) {
        this.userId = userId;
        this.products = products;
        this.userUsedCoupons = userUsedCoupons;
        this.state = state;
    }
}

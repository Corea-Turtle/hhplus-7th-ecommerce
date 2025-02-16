package kr.hhplus.be.server.domain.integration_test.purchase_order;

import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.product.ProductRepository;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrder;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrderService;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrderState;
import kr.hhplus.be.server.domain.user_coupon.UserCoupon;
import kr.hhplus.be.server.domain.user_coupon.UserCouponRepository;
import kr.hhplus.be.server.infrastructure.purchase_order.PurchaseOrderRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Testcontainers
class PurchaseOrderServiceIntegrationTest {

    @Autowired
    PurchaseOrderRepositoryImpl purchaseOrderRepository;

    @Autowired
    PurchaseOrderService purchaseOrderService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserCouponRepository userCouponRepository;

    @DisplayName("[실패]주문아이디로 주문을 조회하지만 PENDING 상태가 아님")
    @Test
    void getPurchaseOrderExistFailNotPENDING() {
        //given
        //상품 추가
        Product product1 = new Product("상품1", 10000);
        Product product2 = new Product("상품2", 20000);
        productRepository.save(product1);
        productRepository.save(product2);
        List<Product> products = new ArrayList<Product>();
        products.add(product1);
        products.add(product2);

        //쿠폰들 추가
        UserCoupon userCoupon1 = new UserCoupon(1L, 1L);
        userCouponRepository.save(userCoupon1);
        List<UserCoupon> userUsedCoupons = new ArrayList<UserCoupon>();
        userUsedCoupons.add(userCoupon1);

        PurchaseOrder purchaseOrder = new PurchaseOrder(1L, products, userUsedCoupons, PurchaseOrderState.PAYMENT_COMPLETE, 27000);

        //when
        purchaseOrderRepository.save(purchaseOrder);

        //then
        Assertions.assertThatThrownBy(() -> purchaseOrderService.getPurchaseOrderExist(purchaseOrder.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("진행 가능한 주문이 없습니다.");
    }

    @DisplayName("[실패]주문아이디로 주문을 조회하지만 존재하지 않음")
    @Test
    void getPurchaseOrderExistFail() {
        //given
        //given
        //상품 추가
        Product product1 = new Product("상품1", 10000);
        Product product2 = new Product("상품2", 20000);
        productRepository.save(product1);
        productRepository.save(product2);
        List<Product> products = new ArrayList<Product>();
        products.add(product1);
        products.add(product2);

        //쿠폰들 추가
        UserCoupon userCoupon1 = new UserCoupon(1L, 1L);
        userCouponRepository.save(userCoupon1);
        List<UserCoupon> userUsedCoupons = new ArrayList<UserCoupon>();
        userUsedCoupons.add(userCoupon1);

        PurchaseOrder purchaseOrder = new PurchaseOrder( products, userUsedCoupons, PurchaseOrderState.ORDER_PENDING, 27000);

        //when
        purchaseOrderRepository.save(purchaseOrder);

        //then
        Assertions.assertThatThrownBy(() -> purchaseOrderService.getPurchaseOrderExist(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 아이디에 해당하는 주문이 없습니다.");

    }

    @DisplayName("[성공]주문아이디로 존재하는 주문을 반환한다.")
    @Test
    void getPurchaseOrderExist() {
        //given
        //상품 추가
        Product product1 = new Product("상품1", 10000);
        Product product2 = new Product("상품2", 20000);
        productRepository.save(product1);
        productRepository.save(product2);
        List<Product> products = new ArrayList<Product>();
        products.add(product1);
        products.add(product2);

        //쿠폰들 추가
        UserCoupon userCoupon1 = new UserCoupon(1L, 1L);
        userCouponRepository.save(userCoupon1);
        List<UserCoupon> userUsedCoupons = new ArrayList<UserCoupon>();
        userUsedCoupons.add(userCoupon1);

        PurchaseOrder purchaseOrder = new PurchaseOrder(1L, products, userUsedCoupons, PurchaseOrderState.ORDER_PENDING, 27000);

        //when
        purchaseOrderRepository.save(purchaseOrder);
        PurchaseOrder purchaseOrderFound = purchaseOrderRepository.findById(purchaseOrder.getId())
                .orElseThrow(()->new IllegalArgumentException(""));

        //then
        Assertions.assertThat(purchaseOrderService.getPurchaseOrderExist(purchaseOrderFound.getId()).getId()).isEqualTo(purchaseOrder.getId());
    }
}
package kr.hhplus.be.server.application.payment;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponType;
import kr.hhplus.be.server.domain.payment.PaymentRepository;
import kr.hhplus.be.server.domain.payment.PaymentService;
import kr.hhplus.be.server.domain.payment.PaymentVendor;
import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.product.ProductRepository;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrder;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrderRepository;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrderService;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrderState;
import kr.hhplus.be.server.domain.statics.StaticsService;
import kr.hhplus.be.server.domain.user.User;
import kr.hhplus.be.server.domain.user.UserRepository;
import kr.hhplus.be.server.domain.user.UserService;
import kr.hhplus.be.server.domain.user_coupon.UserCoupon;
import kr.hhplus.be.server.domain.user_coupon.UserCouponRepository;
import kr.hhplus.be.server.infrastructure.coupon.CouponRepositoryImpl;
import kr.hhplus.be.server.infrastructure.payment.PaymentRepositoryImpl;
import kr.hhplus.be.server.infrastructure.product.ProductRepositoryImpl;
import kr.hhplus.be.server.infrastructure.purchase_order.PurchaseOrderRepositoryImpl;
import kr.hhplus.be.server.infrastructure.user.UserRepositoryImpl;
import kr.hhplus.be.server.infrastructure.user_coupon.UserCouponRepositoryImpl;
import kr.hhplus.be.server.interfaces.api.payment.dto.request.PaymentConfirmRequest;
import kr.hhplus.be.server.interfaces.api.payment.dto.response.PaymentConfirmResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Testcontainers
class PaymentConfirmFacadeTest {


    @Autowired
    CouponRepositoryImpl couponRepository;

    @Autowired
    UserRepositoryImpl userRepository;

    @Autowired
    ProductRepositoryImpl productRepository;

    @Autowired
    UserCouponRepositoryImpl userCouponRepository;

    @Autowired
    PurchaseOrderRepositoryImpl purchaseOrderRepository;

    @Autowired
    PaymentRepositoryImpl paymentRepository;

    @Autowired
    PaymentConfirmFacade paymentConfirmFacade;

    @DisplayName("[실패]결제 승인 실패 - 유저의 잔액이 주문액보다 적음")
    @Test
    void confirmPaymentFailNotEnoughBalance() {
        //given
        User user = new User("잔액부족유저", 100);
        userRepository.save(user);

        //상품 추가
        Product product1 = new Product("상품1", 10000);
        Product product2 = new Product("상품2", 20000);
        productRepository.save(product1);
        productRepository.save(product2);
        List<Product> products = new ArrayList<Product>();
        products.add(product1);
        products.add(product2);

        //쿠폰들 추가
        UserCoupon userCoupon1 = new UserCoupon(user.getId(), 1L);
        userCouponRepository.save(userCoupon1);
        List<UserCoupon> userUsedCoupons = new ArrayList<UserCoupon>();
        userUsedCoupons.add(userCoupon1);

        //주문 추가
        PurchaseOrder purchaseOrder = new PurchaseOrder(user.getId(), products, userUsedCoupons, PurchaseOrderState.ORDER_PENDING, 2000);
        purchaseOrderRepository.save(purchaseOrder);

        //when
        PaymentConfirmRequest request = new PaymentConfirmRequest(user.getId(),purchaseOrder.getId(), PaymentVendor.TEST);

        //then
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                paymentConfirmFacade.confirmPayment(request)
        );
        assertEquals("잔액이 부족합니다.", exception.getMessage());
    }


    @DisplayName("[성공]결제 승인 성공")
    @Test
    void confirmPayment() {
        //given
        User user = new User("잔액있는유저", 300000);
        userRepository.save(user);

        //상품 추가
        Product product1 = new Product("상품1", 10000);
        Product product2 = new Product("상품2", 20000);
        productRepository.save(product1);
        productRepository.save(product2);
        List<Product> products = new ArrayList<Product>();
        products.add(product1);
        products.add(product2);

        Coupon coupon = new Coupon(CouponType.RATE,10,100, LocalDate.parse("9999-12-31"),LocalDate.now());
        couponRepository.save(coupon);

        //쿠폰들 추가
        UserCoupon userCoupon1 = new UserCoupon(user.getId(), coupon.getId());
        userCouponRepository.save(userCoupon1);
        List<UserCoupon> userUsedCoupons = new ArrayList<UserCoupon>();
        userUsedCoupons.add(userCoupon1);

        //주문 추가
        PurchaseOrder purchaseOrder = new PurchaseOrder(user.getId(), products, userUsedCoupons, PurchaseOrderState.ORDER_PENDING, product1.getPrice()+ product2.getPrice());
        purchaseOrderRepository.save(purchaseOrder);

        PaymentConfirmRequest request = new PaymentConfirmRequest(user.getId(),purchaseOrder.getId(), PaymentVendor.TEST);

        PaymentConfirmResponse response = paymentConfirmFacade.confirmPayment(request);
        //then

        Assertions.assertThat(paymentRepository.findTopByUserIdAndPurchaseOrderIdOrderByIdDesc(response.getUserId(),response.getPurchaseOrderId()).get().getUserId()).isEqualTo(user.getId());
        Assertions.assertThat(paymentRepository.findTopByUserIdAndPurchaseOrderIdOrderByIdDesc(response.getUserId(),response.getPurchaseOrderId()).get().getPurchaseOrderId()).isEqualTo(purchaseOrder.getId());

    }
}
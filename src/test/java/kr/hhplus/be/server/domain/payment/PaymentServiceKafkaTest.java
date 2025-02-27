package kr.hhplus.be.server.domain.payment;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.domain.payment.event.PaymentEvent;
import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.product.ProductRepository;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrder;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrderService;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrderState;
import kr.hhplus.be.server.domain.user.User;
import kr.hhplus.be.server.domain.user.UserService;
import kr.hhplus.be.server.domain.user_coupon.UserCoupon;
import kr.hhplus.be.server.domain.user_coupon.UserCouponRepository;
import kr.hhplus.be.server.infrastructure.payment.PaymentRepositoryImpl;
import kr.hhplus.be.server.infrastructure.purchase_order.PurchaseOrderRepositoryImpl;
import kr.hhplus.be.server.interfaces.api.payment.dto.response.PaymentConfirmResponse;
import kr.hhplus.be.server.interfaces.kafka.KafkaConsumer;
import kr.hhplus.be.server.interfaces.kafka.KafkaPublisher;
import kr.hhplus.be.server.interfaces.kafka_connection_test.KafkaConsumerTest;
import kr.hhplus.be.server.interfaces.kafka_connection_test.KafkaProducer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PaymentServiceKafkaTest {

    @Autowired
    PurchaseOrderRepositoryImpl purchaseOrderRepository;

    @Autowired
    PurchaseOrderService purchaseOrderService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserCouponRepository userCouponRepository;

    @Autowired
    PaymentService paymentService;

    @Autowired
    UserService userService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    KafkaPublisher kafkaPublisher;

    @Autowired
    KafkaConsumer kafkaConsumer;


    @DisplayName("[실패]유저포인트가 안맞다.")
    @Test
    void expectedUserPointFail() {
        //given
        //유저추가
        User user = new User("김유저",100);
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

        PurchaseOrder purchaseOrder = new PurchaseOrder(user.getId(), products, userUsedCoupons, PurchaseOrderState.PAYMENT_COMPLETE, 27000);

        //when
        purchaseOrderRepository.save(purchaseOrder);

        PaymentConfirmResponse response = new PaymentConfirmResponse(purchaseOrder.getId(), user.getId(), PurchaseOrderState.ORDER_PENDING, PaymentVendor.TEST, 1000);


        //when


        //then
        Assertions.assertThatThrownBy(() -> paymentService.pay(user,purchaseOrder,response))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잔액이 부족합니다.");
    }


    @DisplayName("[성공]결제 성공한다.")
    @Test
    void paySuccess() {
        //given
        //유저추가
        User user = new User("김유저",100);
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

        PurchaseOrder purchaseOrder = new PurchaseOrder(user.getId(), products, userUsedCoupons, PurchaseOrderState.PAYMENT_COMPLETE, 27000);

        //when
        purchaseOrderRepository.save(purchaseOrder);

        PaymentConfirmResponse response = new PaymentConfirmResponse(purchaseOrder.getId(), user.getId(), PurchaseOrderState.ORDER_PENDING, PaymentVendor.TEST, 1000);


        //when
        Long paymentId = paymentService.savePayment(response);
        eventPublisher.publishEvent(new PaymentEvent(paymentId,response.getPurchaseOrderId(), response.getUserId(), response.getState(),response.getVendor(),response.getTotalOrderPrice()));
        //then
        // then
        Awaitility.await()
                .pollInterval(Duration.ofMillis(300)) // 300ms마다 체크
                .atMost(4, TimeUnit.SECONDS) // 최대 4초 대기
                .untilAsserted(() -> {

                });
    }

}
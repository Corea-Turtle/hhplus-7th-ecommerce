package kr.hhplus.be.server.application.purchase_order;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponRepository;
import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.coupon.CouponType;
import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.product.ProductService;
import kr.hhplus.be.server.domain.stock.Stock;
import kr.hhplus.be.server.domain.stock.StockRepository;
import kr.hhplus.be.server.domain.stock.StockService;
import kr.hhplus.be.server.domain.user.User;
import kr.hhplus.be.server.domain.user.UserService;
import kr.hhplus.be.server.domain.user_coupon.UserCoupon;
import kr.hhplus.be.server.domain.user_coupon.UserCouponRepository;
import kr.hhplus.be.server.domain.user_coupon.UserCouponService;
import kr.hhplus.be.server.infrastructure.coupon.CouponRepositoryImpl;
import kr.hhplus.be.server.infrastructure.product.ProductRepositoryImpl;
import kr.hhplus.be.server.infrastructure.stock.StockRepositoryImpl;
import kr.hhplus.be.server.infrastructure.user.UserRepositoryImpl;
import kr.hhplus.be.server.infrastructure.user_coupon.UserCouponRepositoryImpl;
import kr.hhplus.be.server.interfaces.api.product.dto.ProductDTO;
import kr.hhplus.be.server.interfaces.api.purchase_order.dto.request.PurchaseOrderCreateRequest;
import kr.hhplus.be.server.interfaces.api.purchase_order.dto.response.PurchaseOrderCreateResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Testcontainers
class OrderCreateFacadeTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepositoryImpl userRepository;

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepositoryImpl productRepository;

    @Autowired
    StockService stockService;

    @Autowired
    StockRepositoryImpl stockRepository;

    @Autowired
    UserCouponService userCouponService;

    @Autowired
    UserCouponRepositoryImpl userCouponRepository;

    @Autowired
    CouponService couponService;

    @Autowired
    CouponRepositoryImpl couponRepository;

    @Autowired
    OrderCreateFacade orderCreateFacade;


    @DisplayName("[실패] 리퀘스트에 담긴 유저아이디로 유저를 찾을 수 없다.")
    @Test
    void createOrderFailNoUser() {
        //given
        Product product1 = new Product("1000원 상품",1000);
        productRepository.save(product1);
        ProductDTO product1Dto = new ProductDTO(product1.getId(),product1.getName(),product1.getPrice(),10);
        Product product2 = new Product("2000원 상품",2000);
        productRepository.save(product2);
        ProductDTO product2Dto = new ProductDTO(product2.getId(),product2.getName(),product2.getPrice(),10);
        List<ProductDTO> products = new ArrayList<>();
        products.add(product1Dto);
        products.add(product2Dto);

        Stock stock1 = new Stock(product1.getId(), 10);
        stockRepository.save(stock1);
        Stock stock2 = new Stock(product2.getId(), 11);
        stockRepository.save(stock2);


        User user = new User("없는유저",200);
        userRepository.save(user);

        Coupon coupon = new Coupon(CouponType.RATE, 10, 10, LocalDate.parse("2024-05-05"),LocalDate.now());
        couponRepository.save(coupon);

        List<UserCoupon> userCoupons = new ArrayList<>();
        UserCoupon userCoupon = new UserCoupon(user.getId(), coupon.getId());
        userCouponRepository.save(userCoupon);

        userCoupons.add(userCoupon);


        PurchaseOrderCreateRequest request = new PurchaseOrderCreateRequest(0L, products, userCoupons);
        //when

        //then
        Assertions.assertThatThrownBy(()->orderCreateFacade.createOrder(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유저가 존재하지 않습니다.");
    }

    @DisplayName("[실패] 리퀘스트에 담긴 주문의 모든 상품의 재고가 0이다.")
    @Test
    void createOrderFailAllProductsStockIsZero() {
        User user = new User("유저1",200000);
        userRepository.save(user);

        //given
        Product product1 = new Product("1000원 상품",1000);
        productRepository.save(product1);
        ProductDTO product1Dto = new ProductDTO(product1.getId(),product1.getName(),product1.getPrice(),10);
        Product product2 = new Product("2000원 상품",2000);
        productRepository.save(product2);
        ProductDTO product2Dto = new ProductDTO(product2.getId(),product2.getName(),product2.getPrice(),10);
        List<ProductDTO> products = new ArrayList<>();
        products.add(product1Dto);
        products.add(product2Dto);

        Stock stock1 = new Stock(product1.getId(), 0);
        stockRepository.save(stock1);
        Stock stock2 = new Stock(product2.getId(), 0);
        stockRepository.save(stock2);




        Coupon coupon = new Coupon(CouponType.RATE, 10, 10, LocalDate.parse("2024-05-05"),LocalDate.now());
        couponRepository.save(coupon);

        List<UserCoupon> userCoupons = new ArrayList<>();
        UserCoupon userCoupon = new UserCoupon(user.getId(), coupon.getId());
        userCouponRepository.save(userCoupon);

        userCoupons.add(userCoupon);


        PurchaseOrderCreateRequest request = new PurchaseOrderCreateRequest(user.getId(), products, userCoupons);

        //when
        //요청하신 모든 상품의 재고가 부족하여 주문 생성이 불가능합니다.
        //then
        Assertions.assertThatThrownBy(()->orderCreateFacade.createOrder(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("요청하신 모든 상품의 재고가 부족하여 주문 생성이 불가능합니다.");
    }

    @DisplayName("[성공] 주문 발행에 성공한다.")
    @Test
    void createOrder() {
        //given
        Product product1 = new Product("1000원 상품",1000);
        productRepository.save(product1);
        ProductDTO product1Dto = new ProductDTO(product1.getId(),product1.getName(),product1.getPrice(),10);
        Product product2 = new Product("2000원 상품",2000);
        productRepository.save(product2);
        ProductDTO product2Dto = new ProductDTO(product2.getId(),product2.getName(),product2.getPrice(),10);
        List<ProductDTO> products = new ArrayList<>();
        products.add(product1Dto);
        products.add(product2Dto);

        Stock stock1 = new Stock(product1.getId(), 10);
        stockRepository.save(stock1);
        Stock stock2 = new Stock(product2.getId(), 11);
        stockRepository.save(stock2);

        User user = new User("없는유저",2000000);
        userRepository.save(user);

        Coupon coupon = new Coupon(CouponType.RATE, 10, 10, LocalDate.parse("2024-05-05"),LocalDate.now());
        couponRepository.save(coupon);

        List<UserCoupon> userCoupons = new ArrayList<>();
        UserCoupon userCoupon = new UserCoupon(user.getId(), coupon.getId());
        userCouponRepository.save(userCoupon);

        userCoupons.add(userCoupon);


        PurchaseOrderCreateRequest request = new PurchaseOrderCreateRequest(user.getId(), products, userCoupons);

        //when
        PurchaseOrderCreateResponse response = orderCreateFacade.createOrder(request);
        //then

        Assertions.assertThat(response.getUserId()).isEqualTo(user.getId());


    }
}
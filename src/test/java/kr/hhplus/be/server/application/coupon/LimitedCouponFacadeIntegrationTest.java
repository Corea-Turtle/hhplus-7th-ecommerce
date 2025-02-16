package kr.hhplus.be.server.application.coupon;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponRepository;
import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.coupon.CouponType;
import kr.hhplus.be.server.domain.user.User;
import kr.hhplus.be.server.domain.user.UserRepository;
import kr.hhplus.be.server.domain.user.UserService;
import kr.hhplus.be.server.domain.user_coupon.UserCoupon;
import kr.hhplus.be.server.domain.user_coupon.UserCouponRepository;
import kr.hhplus.be.server.domain.user_coupon.UserCouponService;
import kr.hhplus.be.server.infrastructure.coupon.CouponRepositoryImpl;
import kr.hhplus.be.server.infrastructure.user.UserRepositoryImpl;
import kr.hhplus.be.server.infrastructure.user_coupon.UserCouponRepositoryImpl;
import kr.hhplus.be.server.interfaces.api.coupon.dto.request.CouponLimitedIssueRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@Testcontainers
class LimitedCouponFacadeIntegrationTest {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private CouponRepositoryImpl couponRepository;

    @Autowired
    private UserCouponRepositoryImpl userCouponRepository;

    @Autowired
    private LimitedCouponFacade limitedCouponFacade;

    @Autowired
    private UserService userService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private UserCouponService userCouponService;



    private User testUser;
    private Coupon testCoupon;

    @BeforeEach
    void setUp() {
        testUser = new User("테스트유저", 1000);
        userRepository.save(testUser);
        testCoupon = new Coupon(CouponType.RATE,10,100, LocalDate.parse("2025-03-03"), LocalDate.now());
        couponRepository.save(testCoupon);

    }

    @Test
    @DisplayName("쿠폰 발급 통합 테스트")
    void testIssueLimitedCoupon() {
        // Given
        CouponLimitedIssueRequest request = new CouponLimitedIssueRequest(testUser.getId(), testCoupon.getId());

        // When
        limitedCouponFacade.issuelimitedCoupon(request);

        // Then
        UserCoupon userCouponFound = userCouponRepository.findByUserIdAndCouponId(testUser.getId(), testCoupon.getId())
                .orElseThrow(() -> new IllegalStateException("쿠폰이 정상적으로 발급되지 않음"));

        Assertions.assertThat(userCouponFound.getUserId()).isEqualTo(testUser.getId());
        Assertions.assertThat(userCouponFound.getCouponId()).isEqualTo(testCoupon.getId());
    }
}
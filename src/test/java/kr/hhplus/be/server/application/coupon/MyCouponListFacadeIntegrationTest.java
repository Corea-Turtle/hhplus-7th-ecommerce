package kr.hhplus.be.server.application.coupon;

import jakarta.transaction.Transactional;

import kr.hhplus.be.server.domain.user_coupon.UserCouponService;
import kr.hhplus.be.server.domain.user_coupon.UserCouponState;
import kr.hhplus.be.server.interfaces.api.user_coupon.dto.request.UserCouponListRequest;
import kr.hhplus.be.server.interfaces.api.user_coupon.dto.response.UserCouponListResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Transactional
@Testcontainers
class MyCouponListFacadeIntegrationTest {

    @Autowired
    UserCouponService userCouponService;

    @DisplayName("유저가 가지고 있는 쿠폰을 모두 조회한다.")
    @Test
    void getMyCouponList() {
        //given
        UserCouponListRequest userRequest = new UserCouponListRequest(1L);
        //when
        UserCouponListResponse userResponse = userCouponService.getUserCouponsByUserId(userRequest);
        //then
        Assertions.assertThat(userResponse.getUserCoupons()).hasSize(2); // 쿠폰 개수 확인

        Assertions.assertThat(userResponse.getUserCoupons())
                .extracting("state")
                .containsOnly(UserCouponState.AVAILABLE); // 모든 쿠폰이 AVAILABLE 상태인지 확인

        Assertions.assertThat(userResponse.getUserCoupons())
                .extracting("couponId")
                .containsExactlyInAnyOrder(1L, 2L); // couponId 값이 1과 2인지 확인

    }
}
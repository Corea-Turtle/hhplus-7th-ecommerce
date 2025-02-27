package kr.hhplus.be.server.domain.integration_test.user_coupon;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponType;
import kr.hhplus.be.server.domain.user.User;
import kr.hhplus.be.server.domain.user_coupon.UserCoupon;
import kr.hhplus.be.server.domain.user_coupon.UserCouponRepository;
import kr.hhplus.be.server.domain.user_coupon.UserCouponService;
import kr.hhplus.be.server.domain.user_coupon.UserCouponState;
import kr.hhplus.be.server.infrastructure.coupon.CouponRepositoryImpl;
import kr.hhplus.be.server.infrastructure.user.UserRepositoryImpl;
import kr.hhplus.be.server.interfaces.api.user_coupon.dto.request.UserCouponIssueRequest;
import kr.hhplus.be.server.interfaces.api.user_coupon.dto.request.UserCouponListRequest;
import kr.hhplus.be.server.interfaces.api.user_coupon.dto.response.UserCouponListResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.LocalDateTime;


@SpringBootTest
@Testcontainers
@Transactional
class UserCouponServiceIntegrationTest {

    @Autowired
    CouponRepositoryImpl couponRepository;

    @Autowired
    UserRepositoryImpl userRepository;

    @Autowired
    UserCouponRepository userCouponRepository;

    @Autowired
    UserCouponService userCouponService;

    @DisplayName("[실패]사용자에게 선착순 쿠폰발급시 상태가 AVAILABLE 아니다.")
    @Test
    void IssueCouponToUserStateFail() {
        //given
        UserCouponState state = UserCouponState.USED;
        UserCoupon userCoupon = new UserCoupon(1L, 1L, state);

        userCouponRepository.save(userCoupon);
        //when
        UserCouponIssueRequest request = new UserCouponIssueRequest(userCoupon.getUserId(), userCoupon.getCouponId(), userCoupon.getState());

        //then
        Assertions.assertThatThrownBy(()->userCouponService.issueCouponToUser(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰의 상태가 올바르지 않습니다.");
    }

    @DisplayName("[실패]사용자에게 선착순 쿠폰발급 후 발급 시간이 입력되지 않음")
    @Test
    void IssueCouponToUserIssueTimeFail() {
        //given
        LocalDateTime issuedTime = null;
        UserCouponState state = UserCouponState.AVAILABLE;
        UserCoupon userCoupon = new UserCoupon(1L, 1L, state, issuedTime);
        userCouponRepository.save(userCoupon);

        //when
        UserCouponIssueRequest request = new UserCouponIssueRequest(userCoupon.getUserId(), userCoupon.getCouponId(),issuedTime);

        //then
        Assertions.assertThatThrownBy(()->userCouponService.issueCouponToUser(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("발급일이 올바르지 않습니다.");
    }

    //유저 - 쿠폰 주문/주문취소시 검증
    @DisplayName("[실패]찾으려는 사용자 아이디에 발행된 쿠폰를 찾을 수 없다.")
    @Test
    void updateIssuedCouponStateFail() {
        //given
        UserCoupon userCoupon1 = new UserCoupon(1L,1L);
        UserCoupon userCoupon2 = new UserCoupon(1L,3L);
        userCouponRepository.save(userCoupon1);
        userCouponRepository.save(userCoupon2);

        //when
        Long userIdFind = 3L;
        UserCouponListRequest request = new UserCouponListRequest(userIdFind);


        //then 발급 받은 쿠폰이 존재하지 않습니다.

        UserCouponListResponse response = userCouponService.getUserCouponsByUserId(request);

        Assertions.assertThat(response.getUserCoupons()).isEmpty();
    }


    //유저 - 내 쿠폰 리스트 검증
    @DisplayName("[성공]보유한 쿠폰이 없는 경우 message에 보유한 쿠폰이 없습니다.를 담는다.")
    @Test
    void getUserCouponsByUserIdMessage() {
        //given
        UserCoupon userCoupon1 = new UserCoupon(1L,1L);
        UserCoupon userCoupon2 = new UserCoupon(1L,3L);
        userCouponRepository.save(userCoupon1);
        userCouponRepository.save(userCoupon2);

        //when
        Long userIdFind = 3L;
        UserCouponListRequest request = new UserCouponListRequest(userIdFind);


        UserCouponListResponse response = userCouponService.getUserCouponsByUserId(request);


        String message = "보유한 쿠폰이 없습니다.";

        //then
        Assertions.assertThat(response.getMessage()).isEqualTo("보유한 쿠폰이 없습니다.");
    }

    @DisplayName("[성공]보유한 쿠폰 리스트를 반환하는 응답 객체를 반환한다.")
    @Test
    void getUserCouponsByUserId() {

        User user = new User("유저123123",1000000);
        userRepository.save(user);

        Coupon coupon1 = new Coupon(CouponType.RATE, 10, 30, LocalDate.parse("9999-12-31"), LocalDate.now());
        couponRepository.save(coupon1);
        Coupon coupon2 = new Coupon(CouponType.RATE, 20, 30, LocalDate.parse("9999-12-31"), LocalDate.now());
        couponRepository.save(coupon2);

        //given
        UserCoupon userCoupon1 = new UserCoupon(user.getId(),coupon1.getId());
        UserCoupon userCoupon2 = new UserCoupon(user.getId(),coupon2.getId());
        userCouponRepository.save(userCoupon1);
        userCouponRepository.save(userCoupon2);

        //when
        Long userIdFind = 1L;
        UserCouponListRequest request = new UserCouponListRequest(userIdFind);


        UserCouponListResponse response = userCouponService.getUserCouponsByUserId(request);

        //then
        Assertions.assertThat(response.getUserCoupons()).hasSize(response.getUserCoupons().size());
    }
}
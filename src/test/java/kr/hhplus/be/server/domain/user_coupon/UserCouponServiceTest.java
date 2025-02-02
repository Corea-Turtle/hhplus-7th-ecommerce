package kr.hhplus.be.server.domain.user_coupon;

import kr.hhplus.be.server.domain.user_coupon.infrastructure.UserCouponRepositoryImpl;
import kr.hhplus.be.server.interfaces.api.user_coupon.dto.request.UserCouponIssueRequest;
import kr.hhplus.be.server.interfaces.api.user_coupon.dto.request.UserCouponListRequest;
import kr.hhplus.be.server.interfaces.api.user_coupon.dto.response.UserCouponListResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class UserCouponServiceTest {

//선착순 쿠폰 발급 관련 검증
    @DisplayName("[실패]사용자에게 선착순 쿠폰발급시 상태가 AVAILABLE 아니다.")
    @Test
    void IssueCouponToUserStateFail() {
        //given
        LocalDateTime issuedDate = LocalDateTime.parse("2024-01-01T15:30:00");
        UserCouponState state = UserCouponState.USED;
        UserCoupon userCoupon = new UserCoupon(1L, 1L, 1L, state, issuedDate);

        //when
        //then
        Assertions.assertThat(userCoupon.getState()).isNotEqualTo(UserCouponState.AVAILABLE);
    }

    @DisplayName("[실패]사용자에게 선착순 쿠폰발급 후 발급 시간이 입력되지 않음")
    @Test
    void IssueCouponToUserIssueTimeFail() {
        //given
        LocalDateTime issuedTime = null;
        UserCouponState state = UserCouponState.AVAILABLE;
        UserCoupon userCoupon = new UserCoupon(1L, 1L, 1L, state, issuedTime);

        //when
        //then
        Assertions.assertThat(userCoupon.getIssuedTime()).isNull();
    }

//유저 - 쿠폰 주문/주문취소시 검증
    @DisplayName("[실패]찾으려는 사용자 아이디에 발행된 쿠폰를 찾을 수 없다.")
    @Test
    void updateIssuedCouponStateFail() {
        //given
        List<UserCoupon> userCoupons = new ArrayList<UserCoupon>();
        UserCoupon userCoupon1 = new UserCoupon(1L,1L);
        UserCoupon userCoupon2 = new UserCoupon(1L,3L);

        userCoupons.add(userCoupon1);
        userCoupons.add(userCoupon2);

        //when
        Long UserIdFind = 3L;

        //then 발급 받은 쿠폰이 존재하지 않습니다.

        List<UserCoupon> foundCoupons = new ArrayList<UserCoupon>();
        for(UserCoupon coupon : userCoupons){
            if(coupon.getUserId().equals(UserIdFind)){
                foundCoupons.add(coupon);
            }
        }
        Assertions.assertThat(foundCoupons).isEmpty();
    }


//유저 - 내 쿠폰 리스트 검증
    @DisplayName("[성공]보유한 쿠폰이 없는 경우 message에 보유한 쿠폰이 없습니다.를 담는다.")
    @Test
    void getUserCouponsByUserIdMessage() {
        //given
        List<UserCoupon> userCoupons = new ArrayList<UserCoupon>();
        UserCoupon userCoupon1 = new UserCoupon(1L,1L);
        UserCoupon userCoupon2 = new UserCoupon(1L,3L);

        userCoupons.add(userCoupon1);
        userCoupons.add(userCoupon2);

        //when
        Long UserIdFind = 3L;

        //then
        List<UserCoupon> foundCoupons = new ArrayList<UserCoupon>();
        for(UserCoupon coupon : userCoupons){
            if(coupon.getUserId().equals(UserIdFind)){
                foundCoupons.add(coupon);
            }
        }

        String message = "보유한 쿠폰이 없습니다.";

        UserCouponListResponse response = new UserCouponListResponse(foundCoupons,message);

        //then
        Assertions.assertThat(response.getMessage()).isEqualTo("보유한 쿠폰이 없습니다.");
    }

    @DisplayName("[성공]보유한 쿠폰 리스트를 반환하는 응답 객체를 반환한다.")
    @Test
    void getUserCouponsByUserId() {
//given
        List<UserCoupon> userCoupons = new ArrayList<UserCoupon>();
        UserCoupon userCoupon1 = new UserCoupon(1L,1L);
        UserCoupon userCoupon2 = new UserCoupon(1L,3L);

        userCoupons.add(userCoupon1);
        userCoupons.add(userCoupon2);

        //when
        Long UserIdFind = 1L;

        //then
        List<UserCoupon> foundCoupons = new ArrayList<UserCoupon>();
        for(UserCoupon coupon : userCoupons){
            if(coupon.getUserId().equals(UserIdFind)){
                foundCoupons.add(coupon);
            }
        }

        UserCouponListResponse response = new UserCouponListResponse(foundCoupons,"");

        //then
        Assertions.assertThat(response.getUserCoupons()).hasSize(2);
    }
}
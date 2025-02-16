package kr.hhplus.be.server.domain.unit_test.coupon;

import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CouponTest {

    @org.junit.jupiter.api.DisplayName("[실패]남은 수량이 0과 같거나 작으면 실패한다.")
    @Test
    void checkCouponRemainQuantityFail() {
        //given
        LocalDate createDate = LocalDate.of(2024, 1, 25);
        LocalDate expiredDate = LocalDate.of(2024, 12, 31);
        Coupon coupon = new Coupon(1L, CouponType.RATE, 10, 0, expiredDate, createDate);
        //when

        boolean hasRemaining = coupon.checkCouponRemainQuantity(1L);
        //then

        assertFalse(hasRemaining, "쿠폰의 남은 수량이 0보다 커야 합니다.");
    }

    @DisplayName("[성공]남은 쿠폰 수량을 체크하여 0보다 크면 true를 반환한다.")
    @Test
    void checkCouponRemainQuantity() {
        //given
        LocalDate createDate = LocalDate.of(2024, 1, 25);
        LocalDate expiredDate = LocalDate.of(2024, 12, 31);
        Coupon coupon = new Coupon(1L, CouponType.RATE, 10, 30, expiredDate, createDate);
        //when

        boolean hasRemaining = coupon.checkCouponRemainQuantity(1L);
        //then

        assertTrue(hasRemaining, "쿠폰의 남은 수량이 0보다 커야 합니다.");
    }


    @DisplayName("[성공]쿠폰 아이디와 바꿀 쿠폰 남은 수량을 받아 새로은 값으로 변경한다.")
    @Test
    void updateRemainQuantity() {
        //given
        LocalDate createDate = LocalDate.of(2024, 1, 25);
        LocalDate expiredDate = LocalDate.of(2024, 12, 31);
        Coupon coupon = new Coupon(1L, CouponType.RATE, 10, 30, expiredDate, createDate);


        //when
        int updateQuantity = 40;
        coupon.updateRemainQuantity(updateQuantity);

        //then
        Assertions.assertThat(coupon.getRemainQuantity()).isEqualTo(40);
    }

    @DisplayName("[실패]실행 후 쿠폰의 현재 남은 수량보다 1개 적지 않으면 실패한다.")
    @Test
    void subtractCouponFail() {
        //given
        LocalDate createDate = LocalDate.of(2024, 1, 25);
        LocalDate expiredDate = LocalDate.of(2024, 12, 31);
        Coupon coupon = new Coupon(1L, CouponType.RATE, 10, 30, expiredDate, createDate);

        //when
        coupon.subtractCoupon(coupon);

        //then
        Assertions.assertThat(coupon.getRemainQuantity()).isEqualTo(29);
    }


    @DisplayName("[성공]쿠폰의 남은 수량을 하나 뺴주고 남은 개수가 0과 같거나 크면 성공한다.")
    @Test
    void subtractCoupon() {
        //given
        LocalDate createDate = LocalDate.of(2024, 1, 25);
        LocalDate expiredDate = LocalDate.of(2024, 12, 31);
        Coupon coupon = new Coupon(1L, CouponType.RATE, 10, 30, expiredDate, createDate);

        //when
        coupon.subtractCoupon(coupon);

        //then
        Assertions.assertThat(coupon.getRemainQuantity()).isGreaterThanOrEqualTo(0);
    }
}
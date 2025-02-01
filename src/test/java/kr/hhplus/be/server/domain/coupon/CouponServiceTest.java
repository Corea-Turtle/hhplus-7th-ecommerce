package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.domain.coupon.infrastructure.CouponRepositoryImpl;
import kr.hhplus.be.server.interfaces.api.coupon.dto.request.CouponCreateRequest;
import kr.hhplus.be.server.interfaces.api.coupon.dto.request.CouponUpdateRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.C;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Mock
    CouponRepositoryImpl couponRepository;

    @InjectMocks
    CouponService couponService;


    @DisplayName("[실패]type이 정률할인인 경우 ValueOfType이 0~100 사이가 아니라서 쿠폰이 생성 실패한다.")
    @Test
    void createCouponTypeDiscountFail() {
        //given
        LocalDate createDate = LocalDate.of(2024, 1, 25);
        LocalDate expiredDate = LocalDate.of(2024, 12, 31);
        Coupon coupon = new Coupon(1L, CouponType.RATE, 101, 30, expiredDate, createDate);
        CouponCreateRequest request = new CouponCreateRequest(coupon.getId(), coupon.getType(), coupon.getValueOfType(), coupon.getRemainQuantity(), coupon.getExpiredDate(),coupon.getCreateDate());

        //when
        //then
        Assertions.assertThatThrownBy(()->couponService.createCoupon(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("정률할인 값을 1~100 값으로 입력해주세요.");
    }

    @DisplayName("[실패]type이 정액할인인 경우 ValueOfType이 0보다 같거나 작으면 쿠폰생성이 실패한다.")
    @Test
    void createCouponTypeRateFail() {
        //given
        LocalDate createDate = LocalDate.of(2024, 1, 25);
        LocalDate expiredDate = LocalDate.of(2024, 12, 31);
        Coupon coupon = new Coupon(1L, CouponType.DISCOUNT, 0, 30, expiredDate, createDate);
        CouponCreateRequest request = new CouponCreateRequest(coupon.getId(), coupon.getType(), coupon.getValueOfType(), coupon.getRemainQuantity(), coupon.getExpiredDate(),coupon.getCreateDate());

        //when
        //then
        Assertions.assertThatThrownBy(()->couponService.createCoupon(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("정액할인 값은 1과 같거나 커야합니다.");

    }

    //type이 정률인경우 ValueOfType은 0~100 사이의 값이다.
    //type이 정액일경우 ValueOfType은 1보다 커야한다.
    //expiredDate는 createDate 를 포함한 이후 날짜이어야한다.
    @DisplayName("[실패]expiredDate는 createDate가 포함된 이후 날짜 혹은 null 값도 가능하다.")
    @Test
    void createCouponTypeDateFail() {
        //given
        LocalDate createDate = LocalDate.of(2024, 12, 25);
        LocalDate expiredDate = LocalDate.of(2024, 1, 25);
        Coupon coupon = new Coupon(4L, CouponType.RATE, 1, 30, expiredDate, createDate);
        CouponCreateRequest request = new CouponCreateRequest(coupon.getId(), coupon.getType(), coupon.getValueOfType(), coupon.getRemainQuantity(), coupon.getExpiredDate(),coupon.getCreateDate());

        //when
        //then
        Assertions.assertThatThrownBy(()->couponService.createCoupon(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("만료일은 생성일 이후 날짜만 가능합니다. 영구는 만료일 x");

    }

    @DisplayName("[실패]쿠폰Id와 변경할 개수를 받아 선착순 쿠폰수를 0보다 작은 값으로 변경한다.")
    @Test
    void updateCoupon() {
        //given
        LocalDate createDate = LocalDate.of(2024, 1, 25);
        LocalDate expiredDate = LocalDate.of(2024, 12, 31);
        Coupon coupon = new Coupon(5L, CouponType.RATE, 0, 30, expiredDate, createDate);
        CouponUpdateRequest request = new CouponUpdateRequest(coupon.getId(), -1);

        //when

        //then
        Assertions.assertThatThrownBy(()->couponService.updateCoupon(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("선착순 쿠폰 수는 0과 같거나 커야합니다.");
    }



    @DisplayName("[실패]쿠폰아이디를 입력받아 쿠폰이 존재하지 않아 에러를 발생시킨다.")
    @Test
    void getCouponByIdFail() {
        //given
        LocalDate createDate = LocalDate.of(2024, 1, 25);
        LocalDate expiredDate = LocalDate.of(2024, 12, 31);
        Coupon coupon = new Coupon(1L, CouponType.RATE, 10, 30, expiredDate, createDate);


        //when
        //then
        Assertions.assertThatThrownBy(()-> couponService.getCouponById(2L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당하는 쿠폰이 없습니다.");
    }

    @DisplayName("[성공]쿠폰아이디를 입력받아 해당 쿠폰을 조회하여 반환한다.")
    @Test
    void getCouponById() {

        //given
        LocalDate createDate = LocalDate.of(2024, 1, 25);
        LocalDate expiredDate = LocalDate.of(2024, 12, 31);
        Coupon coupon = new Coupon(1L, CouponType.RATE, 10, 30, expiredDate, createDate);
        Mockito.when(couponRepository.findById(coupon.getId())).thenReturn(Optional.of(coupon));

        //when
        Coupon couponFound = couponService.getCouponById(coupon.getId());

        //then
        Assertions.assertThat(couponFound.getId()).isEqualTo(coupon.getId());
    }
}
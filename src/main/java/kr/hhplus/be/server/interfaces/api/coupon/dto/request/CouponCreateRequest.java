package kr.hhplus.be.server.interfaces.api.coupon.dto.request;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.coupon.CouponType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class CouponCreateRequest {

    private Long id;

    private CouponType type;

    private int valueOfType; //type별 들어가는 값 (ex 정률 할인시 10%할인이면 10, 정액할인시 - 금액 1000원 할인)

    private int remainQuantity; //발급 수량

    private LocalDate expiredDate;

    private LocalDate createDate;
}

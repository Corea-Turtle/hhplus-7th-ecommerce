package kr.hhplus.be.server.interfaces.api.coupon.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
public class CouponLimitedIssueResponse {
    private HttpStatus code;
}

package kr.hhplus.be.server.interfaces.api.user.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserMyCouponListRequest {
    Long userId;
}

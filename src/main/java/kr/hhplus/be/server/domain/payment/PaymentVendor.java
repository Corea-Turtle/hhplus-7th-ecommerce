package kr.hhplus.be.server.domain.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentVendor {

    TEST("테스트벤더");

    private final String text;
}

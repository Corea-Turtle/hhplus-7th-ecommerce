package kr.hhplus.be.server.interfaces.api.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserMyBalanceResponse {
    Long id;
    long balance;
}

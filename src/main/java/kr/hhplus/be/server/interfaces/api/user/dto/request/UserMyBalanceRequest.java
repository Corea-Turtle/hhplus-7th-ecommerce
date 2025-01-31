package kr.hhplus.be.server.interfaces.api.user.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserMyBalanceRequest {
    Long id;

    public Long getId() {
        return id;
    }

    public UserMyBalanceRequest(Long id) {
        this.id = id;
    }
}

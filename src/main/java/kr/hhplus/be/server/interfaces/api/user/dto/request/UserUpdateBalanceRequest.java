package kr.hhplus.be.server.interfaces.api.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserUpdateBalanceRequest {
    Long id;
    long updateBalance;

    public Long getId() {
        return id;
    }

    public long getUpdateBalance() {
        return updateBalance;
    }

    public UserUpdateBalanceRequest(Long id, long updateBalance) {
        this.id = id;
        this.updateBalance = updateBalance;
    }
}

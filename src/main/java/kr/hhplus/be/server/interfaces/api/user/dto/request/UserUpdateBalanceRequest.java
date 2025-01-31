package kr.hhplus.be.server.interfaces.api.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserUpdateBalanceRequest {
    Long id;
    int updateBalance;

    public Long getId() {
        return id;
    }

    public int getUpdateBalance() {
        return updateBalance;
    }

    public UserUpdateBalanceRequest(Long id, int updateBalance) {
        this.id = id;
        this.updateBalance = updateBalance;
    }
}

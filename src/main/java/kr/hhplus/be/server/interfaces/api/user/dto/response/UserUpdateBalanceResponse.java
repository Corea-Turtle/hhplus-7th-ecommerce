package kr.hhplus.be.server.interfaces.api.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class UserUpdateBalanceResponse {
    long resultBalance;

    public long getResultBalance() {
        return resultBalance;
    }

    public void setResultBalance(long resultBalance) {
        this.resultBalance = resultBalance;
    }

    public UserUpdateBalanceResponse(long resultBalance) {
        this.resultBalance = resultBalance;
    }
}

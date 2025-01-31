package kr.hhplus.be.server.interfaces.api.user.dto.response;

public class UserMyBalanceResponse {
    Long id;
    long balance;

    public Long getId() {
        return id;
    }

    public long getBalance() {
        return balance;
    }

    public UserMyBalanceResponse(Long id, long balance) {
        this.id = id;
        this.balance = balance;
    }
}

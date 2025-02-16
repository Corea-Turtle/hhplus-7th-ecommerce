package kr.hhplus.be.server.domain.unit_test.user;


import kr.hhplus.be.server.domain.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class UserTest {

    @DisplayName("[실패]사용자 잔고와 갱신 포인트의 합이 보유보인트 최대값(1000만)보다 큰 경우 실패한다")
    @Test
    void chargeBalanceFail() {
        //given
        User user = new User(1L, "김커스터머", 10000000L);

        //when
        long currentBalance = user.getBalance();
        int updateBalance = 100;

        long resultBalance = currentBalance + updateBalance;

        //then
        Assertions.assertThatThrownBy(()->user.updateBalance(resultBalance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잔고는 1000만 이상 충전할 수 없습니다.");
    }

    @DisplayName("[성공]사용자 잔고에 포인트를 충전한다")
    @Test
    void chargeBalance() {
        //given
        User user = new User(1L, "김커스터머", 10L);

        //when
        long currentBalance = user.getBalance();
        int updateBalance = 100;

        long resultBalance = currentBalance + updateBalance;

        user.updateBalance(resultBalance);
        //then

        Assertions.assertThat(user.getBalance()).isEqualTo(110);
    }

}
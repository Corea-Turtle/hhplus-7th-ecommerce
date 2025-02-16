package kr.hhplus.be.server.domain.unit_test.user;

import kr.hhplus.be.server.domain.user.User;
import kr.hhplus.be.server.domain.user.UserService;
import kr.hhplus.be.server.infrastructure.user.UserRepositoryImpl;
import kr.hhplus.be.server.interfaces.api.user.dto.request.UserMyBalanceRequest;
import kr.hhplus.be.server.interfaces.api.user.dto.request.UserUpdateBalanceRequest;
import kr.hhplus.be.server.interfaces.api.user.dto.response.UserMyBalanceResponse;
import kr.hhplus.be.server.interfaces.api.user.dto.response.UserUpdateBalanceResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepositoryImpl userRepository;

    @InjectMocks
    UserService userService;

    @DisplayName("[실패]없는 유저 아이디로 유저가 있는지 조회했는데 유저가 없다.")
    @Test
    void getUserBalanceNoUser() {
        //given
        User user = new User( "김유저", 10);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty()); // 존재하지 않는 경우 추가

        //when
        //then
        Assertions.assertThatThrownBy(()->userRepository.findById(1L)
                        .orElseThrow(()->new IllegalArgumentException("유저가 존재하지 않습니다.")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유저가 존재하지 않습니다.");
    }


    @DisplayName("[성공]유저 아이디를 전송하여 현재 포인트 잔고를 보여준다.")
    @Test
    void getUserBalance() {
        //given
        User user = new User( "김유저", 10);
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        UserMyBalanceRequest request = new UserMyBalanceRequest(user.getId());
        UserMyBalanceResponse response = new UserMyBalanceResponse(user.getId(),user.getBalance());

        //when
        User userFound = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        //then
        Assertions.assertThat(userService.getCurrentUserBalance(request).getBalance()).isEqualTo(10);
    }


    @DisplayName("[성공]유저아이디와 업데이트할 포인트 잔고를 입력하면 유저의 현재 잔고에 업데이트할 잔고를 추가한다.")
    @Test
    void updateChargeUserBalance() {
        //given
        User user = new User( "김유저", 10);
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        UserUpdateBalanceRequest request = new UserUpdateBalanceRequest(user.getId(), 100);
        UserMyBalanceRequest bRequest = new UserMyBalanceRequest(user.getId());

        //when
        User userFound = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        UserUpdateBalanceResponse response = userService.updateUserBalance(request);

        user.updateBalance(response.getResultBalance());

        //then
        Assertions.assertThat(userService.getCurrentUserBalance(bRequest).getBalance()).isEqualTo(response.getResultBalance());
    }

}
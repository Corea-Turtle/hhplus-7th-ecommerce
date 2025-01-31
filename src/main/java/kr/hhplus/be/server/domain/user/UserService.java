package kr.hhplus.be.server.domain.user;

import kr.hhplus.be.server.domain.user.infrasturcture.UserRepositoryImpl;
import kr.hhplus.be.server.interfaces.api.user.dto.request.UserMyBalanceRequest;
import kr.hhplus.be.server.interfaces.api.user.dto.request.UserUpdateBalanceRequest;
import kr.hhplus.be.server.interfaces.api.user.dto.response.UserMyBalanceResponse;
import kr.hhplus.be.server.interfaces.api.user.dto.response.UserUpdateBalanceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepositoryImpl userRepository;

    public UserService(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    //유저 포인트 조회
    public UserMyBalanceResponse getCurrentUserBalance(UserMyBalanceRequest request){
        User user = userRepository.findById(request.getId())
                .orElseThrow(()->new IllegalArgumentException("유저가 존재하지 않습니다."));
        return new UserMyBalanceResponse(user.getId(), user.getBalance());
    }

    //유저 포인트 업데이트
    public UserUpdateBalanceResponse updateUserBalance(UserUpdateBalanceRequest request){
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        long resultBalance = user.getBalance() + request.getUpdateBalance();

        user.updateBalance(resultBalance);

        userRepository.save(user);

        return new UserUpdateBalanceResponse(resultBalance);
    }

}

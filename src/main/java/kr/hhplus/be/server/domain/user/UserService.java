package kr.hhplus.be.server.domain.user;

import kr.hhplus.be.server.domain.purchase_order.PurchaseOrder;
import kr.hhplus.be.server.infrastructure.user.UserRepositoryImpl;
import kr.hhplus.be.server.interfaces.api.user.dto.request.UserMyBalanceRequest;
import kr.hhplus.be.server.interfaces.api.user.dto.request.UserUpdateBalanceRequest;
import kr.hhplus.be.server.interfaces.api.user.dto.response.UserMyBalanceResponse;
import kr.hhplus.be.server.interfaces.api.user.dto.response.UserUpdateBalanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepositoryImpl userRepository;

    //유저가 존재하는지 확인
    public User getExistUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("유저가 존재하지 않습니다."));
        return user;
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

    //유저 포인트와 주문 상품 재고읭 포인트 비교
    public void updateUserBalanceGreaterThanOrEqualToTotalOrderPrice(User user, PurchaseOrder purchaseOrder){
        if(user.getBalance() >= purchaseOrder.getTotalPrice()){
            //To do
            //state 변경이 들어가야할까? no 로그만 기록하자
            UserUpdateBalanceRequest updateBalanceRequest = new UserUpdateBalanceRequest(user.getId(),-purchaseOrder.getTotalPrice());

            updateUserBalance(updateBalanceRequest);
        }else{
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }
    }
}

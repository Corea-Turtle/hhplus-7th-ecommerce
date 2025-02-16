package kr.hhplus.be.server.infrastructure.user_coupon;

import kr.hhplus.be.server.domain.user_coupon.UserCoupon;
import kr.hhplus.be.server.domain.user_coupon.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserCouponRepositoryImpl implements UserCouponRepository {

    private final UserCouponJpaRepository userCouponJpaRepository;

    @Override
    public Optional<UserCoupon> findById(Long id) {
        return userCouponJpaRepository.findById(id);
    }

    @Override
    public List<UserCoupon> findAll() {
        return userCouponJpaRepository.findAll();
    }

    @Override
    public List<UserCoupon> findAllByUserId(Long userId) {
        return userCouponJpaRepository.findAllByUserId(userId);
    }

    @Override
    public Optional<UserCoupon> findByUserIdAndCouponId(Long userId, Long couponId) {
        return userCouponJpaRepository.findByUserIdAndCouponId(userId, couponId);
    }

    @Override
    public void save(UserCoupon userCoupon) {
        userCouponJpaRepository.save(userCoupon);
    }
}

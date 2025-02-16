package kr.hhplus.be.server.domain.user_coupon;

import java.util.List;
import java.util.Optional;

public interface UserCouponRepository {

    Optional<UserCoupon> findById(Long id);
    List<UserCoupon> findAll();
    List<UserCoupon> findAllByUserId(Long userId);
    Optional<UserCoupon> findByUserIdAndCouponId(Long userId, Long couponId);
    void save(UserCoupon userCoupon);

}

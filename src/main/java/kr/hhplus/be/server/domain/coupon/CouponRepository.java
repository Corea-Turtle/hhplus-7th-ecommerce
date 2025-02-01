package kr.hhplus.be.server.domain.coupon;

import java.util.List;
import java.util.Optional;

public interface CouponRepository {
    Optional<Coupon> findById(Long id);
    List<Coupon> findAll();
    void save(Coupon coupon);
}

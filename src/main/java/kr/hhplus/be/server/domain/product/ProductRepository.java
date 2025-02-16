package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.stock.Stock;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(Long id);
    List<Product> findAll();
    void save(Product product);
}

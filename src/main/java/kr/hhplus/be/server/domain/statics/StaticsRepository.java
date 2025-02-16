package kr.hhplus.be.server.domain.statics;

import kr.hhplus.be.server.interfaces.api.statics.dto.response.StaticsTopResponse;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StaticsRepository {

    Optional<Statics> findById(Long id);
    Optional<Statics> findByProductId(Long productId);
    List<StaticsTopResponse> findTopSales(LocalDate startDate, int limit);
    List<Statics> findAll();
    void save(Statics statics);
}

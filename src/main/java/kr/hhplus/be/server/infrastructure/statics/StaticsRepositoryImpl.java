package kr.hhplus.be.server.infrastructure.statics;

import kr.hhplus.be.server.domain.statics.Statics;
import kr.hhplus.be.server.domain.statics.StaticsRepository;
import kr.hhplus.be.server.interfaces.api.statics.dto.response.StaticsTopResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class StaticsRepositoryImpl implements StaticsRepository {

    private final StaticsJpaRepository staticsJpaRepository;

    @Override
    public Optional<Statics> findById(Long id) {
        return staticsJpaRepository.findById(id);
    }

    @Override
    public Optional<Statics> findByProductId(Long productId) {
        return staticsJpaRepository.findByProductId(productId);
    }

    @Override
    public List<StaticsTopResponse> findTopSales(LocalDate startDate, int limit) {
        return staticsJpaRepository.findTopSales(startDate, limit);
    }

    @Override
    public List<Statics> findAll() {
        return staticsJpaRepository.findAll();
    }

    @Override
    public void save(Statics statics) {
        staticsJpaRepository.save(statics);
    }
}

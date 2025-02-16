package kr.hhplus.be.server.infrastructure.statics;

import kr.hhplus.be.server.domain.statics.Statics;
import kr.hhplus.be.server.interfaces.api.statics.dto.response.StaticsTopResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StaticsJpaRepository extends JpaRepository<Statics, Long> {

    Optional<Statics> findByProductId(Long productId);

    @Query(value = "SELECT s.product_id AS productId, s.product_name AS productName, SUM(s.sales_volume) AS totalVolume " +
            "FROM statics s " +
            "WHERE s.sales_date >= :startDate " +
            "GROUP BY s.product_id, s.product_name " +
            "ORDER BY totalVolume DESC " +
            "LIMIT :limit ", nativeQuery = true)
    List<StaticsTopResponse> findTopSales(@Param("startDate") LocalDate startDate, @Param("limit") int limit);

}

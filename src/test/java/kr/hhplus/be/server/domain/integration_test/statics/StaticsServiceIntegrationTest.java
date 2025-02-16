package kr.hhplus.be.server.domain.integration_test.statics;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.domain.statics.StaticsService;
import kr.hhplus.be.server.infrastructure.statics.StaticsRepositoryImpl;
import kr.hhplus.be.server.interfaces.api.statics.dto.response.StaticsTopResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Testcontainers
@Transactional
class StaticsServiceIntegrationTest {
    @Autowired
    StaticsRepositoryImpl staticsRepository;

    @Autowired
    StaticsService staticsService;


    @DisplayName("상위 상품이 순서대로 출력된다.")
    @Test
    void getTop5DayAndLimit() {
        // given
        LocalDate startDate = LocalDate.now().minusDays(3);

        List<StaticsTopResponse> response = staticsRepository.findTopSales(startDate, 5);


        // when
        List<StaticsTopResponse> result = staticsService.getTop5DayAndLimit(3, 5);

        // then
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result.get(0).getProductName()).isEqualTo(response.get(0).getProductName()); // 300
        Assertions.assertThat(result.get(1).getProductName()).isEqualTo(response.get(1).getProductName()); // 200
        Assertions.assertThat(result.get(2).getProductName()).isEqualTo(response.get(2).getProductName()); // 100
        Assertions.assertThat(result.get(3).getProductName()).isEqualTo(response.get(3).getProductName()); // 100
        Assertions.assertThat(result.get(4).getProductName()).isEqualTo(response.get(4).getProductName()); // 100
    }
}
package kr.hhplus.be.server.domain.unit_test.statics;

import kr.hhplus.be.server.domain.statics.StaticsService;
import kr.hhplus.be.server.infrastructure.statics.StaticsRepositoryImpl;
import kr.hhplus.be.server.interfaces.api.statics.dto.response.StaticsTopResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class StaticsServiceTest {

    @Mock
    private StaticsRepositoryImpl staticsRepository;

    @InjectMocks
    private StaticsService staticsService;


    @DisplayName("상위 상품이 순서대로 출력된다.")
    @Test
    void getTop5DayAndLimit() {
        // given
        LocalDate startDate = LocalDate.now().minusDays(3);

        List<StaticsTopResponse> response = List.of(
                new StaticsTopResponse(1L, "랭킹1위상품", BigDecimal.valueOf(500)),
                new StaticsTopResponse(2L, "랭킹2위상품", BigDecimal.valueOf(400)),
                new StaticsTopResponse(3L, "랭킹3위상품", BigDecimal.valueOf(300)),
                new StaticsTopResponse(4L, "랭킹4위상품", BigDecimal.valueOf(200)),
                new StaticsTopResponse(5L, "랭킹5위상품", BigDecimal.valueOf(100))
        );
        Mockito.when(staticsRepository.findTopSales(startDate, 5)).thenReturn(response);

        // when
        List<StaticsTopResponse> result = staticsService.getTop5DayAndLimit(3, 5);

        // then
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result.get(0).getProductName()).isEqualTo("랭킹1위상품"); // 300
        Assertions.assertThat(result.get(1).getProductName()).isEqualTo("랭킹2위상품"); // 200
        Assertions.assertThat(result.get(2).getProductName()).isEqualTo("랭킹3위상품"); // 100
        Assertions.assertThat(result.get(3).getProductName()).isEqualTo("랭킹4위상품"); // 100
        Assertions.assertThat(result.get(4).getProductName()).isEqualTo("랭킹5위상품"); // 100
    }
}
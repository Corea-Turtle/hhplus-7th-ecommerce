package kr.hhplus.be.server.domain.statics;

import kr.hhplus.be.server.infrastructure.statics.StaticsRepositoryImpl;
import kr.hhplus.be.server.interfaces.api.statics.dto.response.StaticsTopResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StaticsService {

    private final StaticsRepositoryImpl staticsRepository;

    //Statics에 추가(거 뭐냐 결제 할떄 추가)
    public void insertStatics(Long productId, String productName, long salesVolume){
        Statics statics = new Statics(productId, productName, salesVolume, LocalDate.now());
        staticsRepository.save(statics);
    }

    //Statics 조회
    public List<StaticsTopResponse> getTop5DayAndLimit(int day, int limit){
        LocalDate startDate = LocalDate.now().minusDays(day);
        return staticsRepository.findTopSales(startDate, limit);
    }
}

package kr.hhplus.be.server.interfaces.api.statics.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class StaticsTopResponse {
    private Long productId;

    private String productName;

    private BigDecimal totalVolume;

}

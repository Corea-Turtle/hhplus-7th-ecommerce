package kr.hhplus.be.server.domain.statics;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Statics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private String productName;

    private long salesVolume;

    private LocalDate salesDate;

    public Statics(Long productId, String productName,long salesVolume, LocalDate salesDate) {
        this.productId = productId;
        this.productName = productName;
        this.salesVolume = salesVolume;
        this.salesDate = salesDate;
    }

    public void setSalesVolume(long salesVolume) {
        this.salesVolume = salesVolume;
    }
}

package kr.hhplus.be.server.domain.user_coupon;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "user_coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long couponId;

    @Enumerated(EnumType.STRING)
    private UserCouponState state;

    @Column(nullable = true)
    private LocalDateTime issuedTime;  //발급받은 시간

    @Column(nullable = true)
    private LocalDateTime usedTime;  //사용한 시간

    //UnitTest용 생성자
    public UserCoupon(Long id, Long userId, Long couponId, UserCouponState state, LocalDateTime issuedTime) {
        this.id = id;
        this.userId = userId;
        this.couponId = couponId;
        this.state = state;
        this.issuedTime = issuedTime;
    }

    //통합테스트용 생성자
    public UserCoupon(Long userId, Long couponId) {
        this.userId = userId;
        this.couponId = couponId;
        this.state = UserCouponState.AVAILABLE;
        this.issuedTime = LocalDateTime.now();
    }
    //통합테스트용 생성자
    public UserCoupon(Long userId, Long couponId,UserCouponState state) {
        this.userId = userId;
        this.couponId = couponId;
        this.state = state;
        this.issuedTime = LocalDateTime.now();
    }

    public UserCoupon(Long userId, Long couponId, UserCouponState state, LocalDateTime issuedTime) {
        this.userId = userId;
        this.couponId = couponId;
        this.state = state;
        this.issuedTime = issuedTime;
    }

    public UserCoupon(Long userId, Long couponId, LocalDateTime issuedTime) {
        this.userId = userId;
        this.couponId = couponId;
        this.state = UserCouponState.AVAILABLE;
        this.issuedTime = issuedTime;
    }

    public void updateStateToAvailable(){
        this.state = UserCouponState.AVAILABLE;
    }

    public void updateStateToUsed(){
        this.state = UserCouponState.USED;
    }
}


    //테스트용

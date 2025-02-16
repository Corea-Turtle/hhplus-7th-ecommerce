package kr.hhplus.be.server.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@Entity
@Table(name="user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private long balance;

    public User(Long id, String name, long balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public User(String name, long balance) {
        this.name = name;
        this.balance = balance;
    }

//해당 유저의 쿠폰 관리 - 쓸필요 없는듯
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    List<userCoupon> userCoupons;

    //포인트 잔액 증가/감소
    public void updateBalance(long updatedBalance){
        if(updatedBalance > 10000000){
            throw new IllegalArgumentException("잔고는 1000만 이상 충전할 수 없습니다.");
        }
        this.balance = updatedBalance;
    }

    //보유 쿠폰 추가 - 쓸필요 없는듯
//    public void addUserCoupon(User user, Coupon coupon){
//        userCoupons.add(new UserCoupon(user, coupon));
//    }


}

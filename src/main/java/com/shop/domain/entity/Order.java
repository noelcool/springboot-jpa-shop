package com.shop.domain.entity;

import com.shop.domain.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 한명의 회원이 여러번 주문을 할 수 있으므로 N:1

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order",
            cascade = CascadeType.ALL, // 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 전이
            orphanRemoval = true, // 고아 객체 제거
            fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime regTime;

    private LocalDateTime updateTime;


}

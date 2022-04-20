package com.shop.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

}

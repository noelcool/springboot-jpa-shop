package com.shop.domain.entity;

import com.shop.domain.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //상품 코드

    @Column(nullable = false, length = 50)
    private String itemName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockNumber; //재고 수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; //상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; //상품 판매 상태

    private LocalDateTime regTime; //등록 시간

    private LocalDateTime updateTime; //수정 시간

}

/*
CREATE TABLE `item` (
  `id` bigint(20) NOT NULL,
  `item_detail` longtext NOT NULL,
  `item_name` varchar(50) NOT NULL,
  `item_sell_status` varchar(255) DEFAULT NULL,
  `price` int(11) NOT NULL,
  `reg_time` datetime(6) DEFAULT NULL,
  `stock_number` int(11) NOT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
*/
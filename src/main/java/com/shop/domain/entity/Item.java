package com.shop.domain.entity;

import com.shop.domain.constant.ItemSellStatus;
import com.shop.domain.dto.ItemFormDto;
import com.shop.exception.OutOfStockException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "item")
public class Item extends BaseEntity {

    @Id
    @Column(name = "item_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //상품 코드

    @Column(nullable = false, length = 50)
    private String itemNm;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockNumber; //재고 수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; //상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; //상품 판매 상태

    @OneToMany(mappedBy = "item",
            cascade = CascadeType.ALL, // 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 전이
            orphanRemoval = true, // 고아 객체 제거
            fetch = FetchType.LAZY)
    private List<CartItem> cartItems = new ArrayList<>();

    @Builder
    public Item(String itemNm, int price, int stockNumber, String itemDetail, ItemSellStatus itemSellStatus) {
        this.itemNm = itemNm;
        this.price = price;
        this.stockNumber = stockNumber;
        this.itemDetail = itemDetail;
        this.itemSellStatus = itemSellStatus;
    }

    public Item() {

    }

    public void updateItem(ItemFormDto dto) {
        this.itemNm = dto.getItemNm();
        this.price = dto.getPrice();
        this.stockNumber = dto.getStockNumber();
        this.itemDetail = dto.getItemDetail();
        this.itemSellStatus = dto.getItemSellStatus();
    }

    public void removeStock(int stockNumber) {
        int restStock = this.stockNumber - stockNumber;
        if(restStock < 0) {
            throw new OutOfStockException("재고가 부족합니다. (현재 재고 수량 : " + this.stockNumber + ")");
        }
        this.stockNumber = restStock;
    }

    public void addStock(int stockNumber) {
        this.stockNumber += stockNumber;
    }

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
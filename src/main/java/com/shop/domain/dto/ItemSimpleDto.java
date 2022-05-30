package com.shop.domain.dto;

import com.shop.domain.constant.ItemSellStatus;
import com.shop.domain.entity.Item;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;


public record ItemSimpleDto(
        Long id, String itemNm, Integer price
) {

    private ItemSimpleDto(Item item) {
        this(item.getId(), item.getItemNm(), item.getPrice());
    }

    public static ItemSimpleDto of(Item item) {
        return new ItemSimpleDto(item);
    }
}


/*

@Getter
@ToString
@EqualsAndHashCode
public class ItemSimpleDto {
    private Long id;
    private String itemNm;
    private Integer price;

    public ItemSimpleDto(Long id, String itemNm, Integer price) {
        this.id = id;
        this.itemNm = itemNm;
        this.price = price;
    }

    private ItemSimpleDto(Item item) {
        this(item.getId(), item.getItemNm(), item.getPrice());
    }

    public static ItemSimpleDto of(Item item) {
        return new ItemSimpleDto(item);
    }
}


 */
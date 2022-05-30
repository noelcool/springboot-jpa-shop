package com.shop.domain.dto.request;

import com.shop.domain.constant.ItemSellStatus;
import com.shop.domain.dto.api.ItemDto;
import com.shop.domain.entity.Item;
import org.modelmapper.ModelMapper;

public record ItemRequest(
        String itemNm,
        Integer price,
        Integer stockNumber,
        String itemDetail,
        ItemSellStatus itemSellStatus
) {

    public static ItemRequest of(
            String itemNm,
            Integer price,
            Integer stockNumber,
            String itemDetail,
            ItemSellStatus itemSellStatus
    ) {
        return new ItemRequest(
                itemNm,
                price,
                stockNumber,
                itemDetail,
                itemSellStatus
        );
    }

    public ItemDto toDto() {
        return ItemDto.of(
                this.itemNm(),
                this.price(),
                this.stockNumber(),
                this.itemDetail(),
                this.itemSellStatus(),
                null,
                null
        );
    }

    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem() {
        return modelMapper.map(this, Item.class); // 엔티티와 dto 간의 데이터를 복사해서 복사한 객체를 리턴
    }
}

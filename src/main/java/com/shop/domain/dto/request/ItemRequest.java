package com.shop.domain.dto.request;

import com.shop.domain.constant.ItemSellStatus;
import com.shop.domain.dto.api.ItemDto;

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
}

package com.shop.domain.dto.response;

import com.shop.domain.constant.ItemSellStatus;
import com.shop.domain.dto.api.ItemDto;

public record ItemResponse(
        String itemNm,
        Integer price,
        Integer stockNumber,
        String itemDetail,
        ItemSellStatus itemSellStatus
) {

    public static ItemResponse of(
            String itemNm,
            Integer price,
            Integer stockNumber,
            String itemDetail,
            ItemSellStatus itemSellStatus
    ) {
        return new ItemResponse(
                itemNm,
                price,
                stockNumber,
                itemDetail,
                itemSellStatus
        );
    }

    public static ItemResponse from(ItemDto itemDto) {
        if(itemDto == null) return null;
        return ItemResponse.of(
                itemDto.itemNm(),
                itemDto.price(),
                itemDto.stockNumber(),
                itemDto.itemDetail(),
                itemDto.itemSellStatus()
        );
    }
}

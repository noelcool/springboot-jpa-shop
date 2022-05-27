package com.shop.domain.dto.api;

import com.shop.domain.constant.ItemSellStatus;
import com.shop.domain.dto.ItemImgDto;

import java.util.List;

public record ItemDto(
        String itemNm,
        Integer price,
        Integer stockNumber,
        String itemDetail,
        ItemSellStatus itemSellStatus,
        List<ItemImgDto> itemImgDtoList,
        List<Long> itemImgIds
) {

    public static ItemDto of(
            String itemNm,
            Integer price,
            Integer stockNumber,
            String itemDetail,
            ItemSellStatus itemSellStatus,
            List<ItemImgDto> itemImgDtoList,
            List<Long> itemImgIds
    ) {
        return new ItemDto(
                itemNm,
                price,
                stockNumber,
                itemDetail,
                itemSellStatus,
                itemImgDtoList,
                itemImgIds
        );
    }




}

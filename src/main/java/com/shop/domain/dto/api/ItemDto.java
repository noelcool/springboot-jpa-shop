package com.shop.domain.dto.api;

import com.shop.domain.constant.ItemSellStatus;
import com.shop.domain.dto.ItemImgDto;
import com.shop.domain.entity.CartItem;
import com.shop.domain.entity.Item;

import java.util.List;

public record ItemDto(
        Long id,
        String itemNm,
        Integer price,
        Integer stockNumber,
        String itemDetail,
        ItemSellStatus itemSellStatus,
        List<CartItem> cartItems,
        List<ItemImgDto> itemImgDtoList,
        List<Long> itemImgIds
) {
    public static ItemDto of(
            Item item
    ) {
        return new ItemDto(
                item.getId(),
                item.getItemNm(),
                item.getPrice(),
                item.getStockNumber(),
                item.getItemDetail(),
                item.getItemSellStatus(),
                item.getCartItems(),
                null,
                null
        );
    }

    public static ItemDto from(
            Long id,
            String itemNm,
            Integer price,
            Integer stockNumber,
            String itemDetail,
            ItemSellStatus itemSellStatus,
            List<CartItem> cartItems,
            List<ItemImgDto> itemImgDtoList,
            List<Long> itemImgIds
    ) {
        return new ItemDto(
                id,
                itemNm,
                price,
                stockNumber,
                itemDetail,
                itemSellStatus,
                cartItems,
                itemImgDtoList,
                itemImgIds
        );
    }


}

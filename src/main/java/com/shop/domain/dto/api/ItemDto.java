package com.shop.domain.dto.api;

import com.shop.domain.constant.ItemSellStatus;
import com.shop.domain.dto.ItemImgDto;
import com.shop.domain.entity.Item;
import org.modelmapper.ModelMapper;

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

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemDto of(Item item) {
        return modelMapper.map(item, ItemDto.class);
    }



}

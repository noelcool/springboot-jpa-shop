package com.shop.domain.dto.api;

import com.shop.domain.constant.ItemSellStatus;
import com.shop.domain.dto.ItemImgDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

public record ItemRequest(
        @NotBlank String itemNm,
        @NotNull @Positive Integer price,
        String itemDetail,
        @NotNull @PositiveOrZero Integer stockNumber,
        ItemSellStatus itemSellStatus,
        List<ItemImgDto> itemImgDtoList,
        List<Long> itemImgIdList
) {

    public static ItemRequest of(
            String itemNm,
            Integer price,
            String itemDetail,
            Integer stockNumber,
            ItemSellStatus itemSellStatus,
            List<ItemImgDto> itemImgDtoList,
            List<Long> itemImgIdList
    ) {
        return new ItemRequest(
                itemNm,
                price,
                itemDetail,
                stockNumber,
                itemSellStatus,
                itemImgDtoList,
                itemImgIdList
        );
    }
}

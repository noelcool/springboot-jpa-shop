package com.shop.domain.dto.search;

import com.shop.domain.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDto {

    private String searchDateType; // 현재 시간과 상품 등록일을 비교해서 조회(ex : 최근 한달)
    private ItemSellStatus searchSellStatus; // 상품의 판매 상태를 기준으로 조회
    private String searchBy; // 상품 조회 유형
    private String searchQuery = ""; // 조회할 검색어 저장
}

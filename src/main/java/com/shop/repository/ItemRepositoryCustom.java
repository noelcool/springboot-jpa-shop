package com.shop.repository;

import com.shop.domain.dto.search.ItemSearchDto;
import com.shop.domain.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto dto, Pageable pageable);

}

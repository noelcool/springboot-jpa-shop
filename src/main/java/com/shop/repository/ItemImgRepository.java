package com.shop.repository;

import com.shop.domain.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    //test
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);

    ItemImg findByItemIdAndRepImgYn(Long itemId, String repImgYn);

    List<ItemImg> findByItemIdAndImgNameIsNotNullOrderByIdAsc(Long itemId);

    List<Long> findIdByItemIdOrderByIdAsc(Long id);
}

package com.shop.service;

import com.shop.domain.dto.ItemFormDto;
import com.shop.domain.entity.Item;
import com.shop.domain.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;

    private final ItemImgService itemImgService;

    public Long saveItem(ItemFormDto itemDto, List<MultipartFile> itemImgList) throws Exception {
        // 상품 등록
        Item item = itemDto.createItem();
        itemRepository.save(item);

        // 이미지 등록
        for(int i=0; i<itemImgList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if(i == 0) itemImg.setRepImgYn("Y"); // 첫번쨰 이미지를 대표 이미지로 지정
            else itemImg.setRepImgYn("N");
            itemImgService.saveItemImg(itemImg, itemImgList.get(i));
        }

        return item.getId();
    }

}

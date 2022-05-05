package com.shop.service;

import com.shop.domain.dto.ItemFormDto;
import com.shop.domain.dto.ItemImgDto;
import com.shop.domain.dto.search.ItemSearchDto;
import com.shop.domain.entity.Item;
import com.shop.domain.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public ItemFormDto getItem(Long itemId) {
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
//        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
//        for (ItemImg itemImg : itemImgList) {
//            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
//            itemImgDtoList.add(itemImgDto);
//        }
        //TODO - 위랑 같게 동작하는지 확인
        List<ItemImgDto> itemImgDtoList = itemImgList.stream().map(ItemImgDto::of).collect(Collectors.toList());

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }

    public Long updateItem(ItemFormDto dto, List<MultipartFile> itemImgFileList) throws Exception {
        Item item = itemRepository.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);
        item.updateItem(dto);

        List<Long> itemImgIds = dto.getItemImgIds();

        for(int i=0; i<itemImgFileList.size(); i++) {
           itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }
        return item.getId();
    }

    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto dto, Pageable pageable) {
        return itemRepository.getAdminItemPage(dto, pageable);
    }

}

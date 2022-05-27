package com.shop.service;

import com.shop.domain.constant.ErrorCode;
import com.shop.domain.dto.ItemFormDto;
import com.shop.domain.dto.ItemImgDto;
import com.shop.domain.dto.MainItemDto;
import com.shop.domain.dto.api.ItemDto;
import com.shop.domain.dto.request.ItemRequest;
import com.shop.domain.dto.search.ItemSearchDto;
import com.shop.domain.entity.Item;
import com.shop.domain.entity.ItemImg;
import com.shop.exception.NotFoundException;
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
import java.util.Optional;
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
        itemImgList.forEach(i -> {
            if(!i.getOriginalFilename().equals("")) {
                ItemImg itemImg = new ItemImg();
                if(itemImgList.get(0).getOriginalFilename().equals(i.getOriginalFilename())) {
                    itemImg.setRepImgYn("Y"); // 첫번쨰 이미지를 대표 이미지로 지정
                } else {
                    itemImg.setRepImgYn("N");
                }
                itemImg.setItem(item);
                try {
                    itemImgService.saveItemImg(itemImg, i);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return item.getId();
    }

    @Transactional(readOnly = true)
    public ItemFormDto getItem(Long itemId) {
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

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

    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }

    public List<ItemFormDto> findAll() {
        List<ItemFormDto> temp = itemRepository.findAll().stream().map(ItemFormDto::of).collect(Collectors.toList());
        temp.forEach(t -> {
            List<Long> itemImgIds = itemImgRepository.findByItemIdOrderByIdAsc(t.getId()).stream().map(ItemImg::getId).collect(Collectors.toList());
            //List<ItemImgDto> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(t.getId()).stream().map(ItemImgDto::of).collect(Collectors.toList());
            //t.setItemImgDtoList(itemImgList);
            t.setItemImgIds(itemImgIds);
        });
        return temp;
    }

    public List<ItemFormDto> insert(ItemRequest item) {
        return null;
    }

    public ItemFormDto findById(long id) {
        ItemFormDto temp = itemRepository.findById(id).map(ItemFormDto::of).
                orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_EXCEPTION.getMessage()));
        List<Long> itemImgIds = itemImgRepository.findByItemIdOrderByIdAsc(temp.getId()).stream().map(ItemImg::getId).collect(Collectors.toList());
        temp.setItemImgIds(itemImgIds);
        return temp;
    }
}

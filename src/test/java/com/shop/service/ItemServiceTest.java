package com.shop.service;

import com.shop.domain.constant.ItemSellStatus;
import com.shop.domain.dto.ItemFormDto;
import com.shop.domain.entity.Item;
import com.shop.domain.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemImgRepository itemImgRepository;

    List<MultipartFile> createMultipartFiles() throws Exception {
       List<MultipartFile> multipartFileList = new ArrayList<>();
       for(int i=0; i<5; i++) {
           String path = "/Users/noel/Documents/github_init/shop-img-resource/test";
           String imgName = "image" + i + ".jpg";
           MockMultipartFile multipartFile =
                   new MockMultipartFile(
                           path,
                           imgName,
                           "image/jpg",
                           new byte[]{1,2,3,4});
           multipartFileList.add(multipartFile);
       }
       return multipartFileList;
    }

    @Test
    @DisplayName("상품 등록 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void saveItem() throws Exception {
        ItemFormDto dto = new ItemFormDto();
        dto.setItemNm("테스트 상품");
        dto.setItemSellStatus(ItemSellStatus.SELL);
        dto.setItemDetail("테스트 상품 입니다");
        dto.setPrice(1000);
        dto.setStockNumber(100);

        List<MultipartFile> multiFileList = createMultipartFiles();
        Long itemId = itemService.saveItem(dto, multiFileList);
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

        assertEquals(dto.getItemNm(), item.getItemNm());
        assertEquals(dto.getItemSellStatus(), item.getItemSellStatus());
        assertEquals(dto.getItemDetail(), item.getItemDetail());
        assertEquals(dto.getPrice(), item.getPrice());
        assertEquals(dto.getStockNumber(), item.getStockNumber());
        assertEquals(multiFileList.get(0).getOriginalFilename(), itemImgList.get(0).getOriImgName());
    }

}
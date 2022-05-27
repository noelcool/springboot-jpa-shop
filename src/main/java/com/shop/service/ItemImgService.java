package com.shop.service;

import com.shop.domain.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.util.ShopProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

//    @Value("D:/999_noel/shop-img-resource")
//    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";
        String imgPath = "";
        if(!StringUtils.isEmpty(oriImgName)) {
            imgPath = ShopProperties.getImageUploadPath() + "/item";
            imgName = fileService.uploadFile(imgPath,
                    oriImgName,
                    itemImgFile.getBytes());
            imgUrl = "/images/item/" + imgName;
        }
        itemImg.updateItemImg(oriImgName, imgName, imgUrl);
        itemImgRepository.save(itemImg);
    }

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {
        if(!itemImgFile.isEmpty()) {
            String imgPath = ShopProperties.getImageUploadPath() + "/item";
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId).
                    orElseThrow(EntityNotFoundException::new);

            if(!StringUtils.isEmpty(savedItemImg.getImgName())) {
                fileService.deleteFile(imgPath + "/" + savedItemImg.getImgName());
            }
            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(ShopProperties.getImageUploadPath(), oriImgName, itemImgFile.getBytes());
            String imgUrl = "/images/item/" + imgName;
            // 변경된 상품 이미지 set, save를 호출하지 않고 데이텨 변경 감지로 처리(update)
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
        }
    }
}

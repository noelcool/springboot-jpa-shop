package com.shop.controller;

import com.shop.domain.dto.ItemFormDto;
import com.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "/item/itemForm";
    }

    @PostMapping(value = "/admin/item/new")
    public String newItem(@Valid ItemFormDto dto,
                          BindingResult bindingResult,
                          Model model,
                          @RequestParam("itemImgFile") List<MultipartFile> itemImgList) throws Exception {

        if(bindingResult.hasErrors()) {
          return "item/itemForm";
        }

        if(itemImgList.get(0).isEmpty() && dto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력 값입니다");
            return "item/itemForm";
        }
        try {
            itemService.saveItem(dto, itemImgList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러 발생");
            return "item/itemForm";
        }
        return "redirect:/";
    }

}

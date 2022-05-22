package com.shop.controller;

import com.shop.domain.dto.ItemFormDto;
import com.shop.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Tag(name = "item", description = "상품 API")
@RequestMapping(path = "/item")
public class ItemController {

    private final ItemService itemService;

    @GetMapping(value = "/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId) {
        ItemFormDto dto = itemService.getItem(itemId);
        model.addAttribute("item", dto);
        return "item/itemDtl";
    }
}

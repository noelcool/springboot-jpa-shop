package com.shop.controller;

import com.shop.domain.dto.ItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/thymeleaf")
public class ThymeleafExController {


    @GetMapping(value = "/ex01")
    public String thymeleafExample01(Model model) {
        model.addAttribute("data", "ㅌㅏ임리프 예제임");
        return "thymeleafEx/thymeleafEx01";
    }

    @GetMapping(value = "/ex02")
    public String thymeleafExample02(Model model) {
        ItemDto dto = new ItemDto();
        dto.setItemDetail("상품 상세 설명");
        dto.setItemName("테스트 상품1");
        dto.setPrice(10000);
        dto.setRegTime(LocalDateTime.now());
        model.addAttribute("itemDto", dto);
        return "thymeleafEx/thymeleafEx02";
    }

    @GetMapping(value = "/ex03")
    public String thymeleafExample03(Model model) {
        List<ItemDto> itemDtoList = new ArrayList<>();
        for(int i=1; i<=10; i++) {
            ItemDto dto = new ItemDto();
            dto.setItemDetail("상품 상세 설명" + i);
            dto.setItemName("테스트 상품" + i);
            dto.setPrice(10000 * i);
            dto.setRegTime(LocalDateTime.now());
            itemDtoList.add(dto);
        }
        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleafEx/thymeleafEx03";
    }

    @GetMapping(value = "/ex04")
    public String thymeleafExample04(Model model) {
        List<ItemDto> itemDtoList = new ArrayList<>();
        for(int i=1; i<=10; i++) {
            ItemDto dto = new ItemDto();
            dto.setItemDetail("상품 상세 설명" + i);
            dto.setItemName("테스트 상품" + i);
            dto.setPrice(10000 * i);
            dto.setRegTime(LocalDateTime.now());
            itemDtoList.add(dto);
        }
        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleafEx/thymeleafEx04";
    }


    @GetMapping(value = "/ex05")
    public String thymeleafExample05(Model model) {
        List<ItemDto> itemDtoList = new ArrayList<>();
        for(int i=1; i<=10; i++) {
            ItemDto dto = new ItemDto();
            dto.setItemDetail("상품 상세 설명" + i);
            dto.setItemName("테스트 상품" + i);
            dto.setPrice(10000 * i);
            dto.setRegTime(LocalDateTime.now());
            itemDtoList.add(dto);
        }
        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleafEx/thymeleafEx05";
    }

    @GetMapping(value = "/ex06")
    public String thymeleafExample06() {
        return "thymeleafEx/thymeleafEx06";
    }

    @GetMapping(value = "/ex07")
    public String thymeleafExample07(String param1, String param2, Model model) {
        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);
        return "thymeleafEx/thymeleafEx07";
    }

    @GetMapping(value = "/ex08")
    public String thymeleafExample08() {
        return "thymeleafEx/thymeleafEx08";
    }

}

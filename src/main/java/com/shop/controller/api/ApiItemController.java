package com.shop.controller.api;

import com.shop.domain.dto.ItemFormDto;
import com.shop.domain.dto.api.ItemDto;
import com.shop.domain.dto.request.ItemRequest;
import com.shop.domain.dto.response.BaseResponse;
import com.shop.domain.dto.response.CommonResponse;
import com.shop.domain.dto.response.DataResponse;
import com.shop.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@Validated
@RequiredArgsConstructor
@RequestMapping("/api/items")
@Tag(name = "api-items", description = "아이템 조회, 등록")
@RestController
public class ApiItemController {
    private final ItemService itemService;

    @PostMapping(value = "")
    public BaseResponse insert(@RequestBody ItemRequest item) {
        List<ItemFormDto> itemList = itemService.insert(item);
        if(itemList != null) {
            return new DataResponse<>(itemList);
        }
        return new BaseResponse("아이템 목록이 비어있습니다");
    }

    @GetMapping(value = "")
    public CommonResponse<List<ItemFormDto>> findAll() {
        List<ItemFormDto> itemList = itemService.findAll();
        return new CommonResponse<>(itemList);
    }

    @GetMapping(value = "/{id}")
    public CommonResponse<ItemFormDto> findById(@PathVariable("id") long id) {
        ItemFormDto oItem = itemService.findById(id);
        return new CommonResponse<>(oItem);
    }



}

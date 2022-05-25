package com.shop.controller.api;

import com.shop.domain.dto.api.ItemRequest;
import com.shop.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//@Validated
@RequiredArgsConstructor
@RequestMapping("/api/items")
@Tag(name = "api-items", description = "아이템 조회, 등록")
@RestController
public class ItemController {
    private final ItemService itemService;

    @PostMapping(value = "")
    public Map<String, Object> insert(@RequestBody ItemRequest item) {
        Map<String, Object> response = new HashMap<>();
        if(itemService.insert(item)) {
            response.put("result", "success");
        } else {
            response.put("result", "FAIL");
            response.put("reason", "");
        }
        return response;
    }


    @GetMapping(value = "")
    public String Test() {
        return "test";
    }


}

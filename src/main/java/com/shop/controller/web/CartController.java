package com.shop.controller.web;

import com.shop.domain.dto.CartDetailDto;
import com.shop.domain.dto.CartItemDto;
import com.shop.domain.dto.CartOrderDto;
import com.shop.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Tag(name = "web-cart", description = "장바구니 API")
@RequestMapping(path = "/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping(value = "")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid CartItemDto cartItemDto,
                                              BindingResult bindingResult,
                                              Principal principal) {
        if(bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            bindingResult.getFieldErrors().forEach(b -> {
                sb.append(b.getDefaultMessage());
            });
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        String email = principal.getName();
        Long cartItemId;

        try {
            cartItemId = cartService.addCart(cartItemDto, email);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @GetMapping(value = "")
    public String orderHist(Principal principal, Model model) {
        List<CartDetailDto> cartDetailDtoList = cartService.getCartList(principal.getName());
        model.addAttribute("cartItems", cartDetailDtoList);
        return "cart/cartList";
    }

    @PostMapping(value = "/order/list/")
    public @ResponseBody ResponseEntity orderCartItem(@RequestBody CartOrderDto cartOrderDto, Principal principal) {
        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();
        if(cartOrderDtoList == null || cartOrderDtoList.size() == 0) {
            return new ResponseEntity<String>("주문할 상품을 선택해주세요", HttpStatus.FORBIDDEN);
        }

        for(CartOrderDto c : cartOrderDtoList) {
            if(!cartService.validateCartItem(c.getCartItemId(), principal.getName())) {
                return new ResponseEntity<String>("주문 권한이 없습니다", HttpStatus.FORBIDDEN);
            }
        }

        Long orderId = cartService.orderCartItem(cartOrderDtoList, principal.getName());
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

}

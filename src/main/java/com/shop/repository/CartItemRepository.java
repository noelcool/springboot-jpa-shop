package com.shop.repository;

import com.shop.domain.dto.CartDetailDto;
import com.shop.domain.entity.Cart;
import com.shop.domain.entity.CartItem;
import com.shop.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    CartItem findByCartAndItem(Cart cart, Item item);

    @Query("SELECT new com.shop.domain.dto.CartDetailDto(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) " +
            "FROM CartItem ci, ItemImg im " +
            "JOIN ci.item i " +
            "WHERE ci.cart.id = :cartId " +
            "and im.item.id = ci.item.id " +
            "and im.repImgYn = 'Y' " +
            "ORDER BY ci.regTime desc")
    List<CartDetailDto> findCartDetailDtoList(Long cartId);
}

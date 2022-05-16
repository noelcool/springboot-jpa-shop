package com.shop.repository;

import com.shop.domain.entity.Cart;
import com.shop.domain.entity.CartItem;
import com.shop.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    CartItem findByCartAndItem(Cart cart, Item item);
}

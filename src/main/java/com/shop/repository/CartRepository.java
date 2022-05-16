package com.shop.repository;

import com.shop.domain.entity.Cart;
import com.shop.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByMemberId(Long memberId);

    Cart findByMember(Member member);
}

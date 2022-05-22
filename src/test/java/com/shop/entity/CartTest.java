package com.shop.entity;

import com.shop.domain.dto.MemberFormDto;
import com.shop.domain.entity.Cart;
import com.shop.domain.entity.Member;
import com.shop.repository.CartRepository;
import com.shop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties.bk")
class CartTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;

    public Member createMember() {
        MemberFormDto dto = new MemberFormDto();
        dto.setEmail("noel@noel.com");
        dto.setName("noel");
        dto.setAddress("서울시 영등포구 의사당대로83");
        dto.setPassword("1234");
        return Member.createMember(dto, passwordEncoder);
    }

    @Test
    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
    public void findCartAndMemberTest() {
        Member member = createMember();
        memberRepository.save(member);
        Cart cart = new Cart();
        cart.setMember(member);
        cartRepository.save(cart);
        em.flush();
        /*
        * insert
            into
                member
                (address, email, name, password, role, member_id)
            values
                (?, ?, ?, ?, ?, ?)
              insert
            into
                cart
                (member_id, cart_id)
            values
                (?, ?)
        * */
        em.clear();

        Cart saveCart = cartRepository.findById(cart.getId()).orElseThrow(EntityNotFoundException::new);
        /*
        *
        select
            cart0_.cart_id as cart_id1_0_0_,
            cart0_.member_id as member_i2_0_0_,
            member1_.member_id as member_i1_2_1_,
            member1_.address as address2_2_1_,
            member1_.email as email3_2_1_,
            member1_.name as name4_2_1_,
            member1_.password as password5_2_1_,
            member1_.role as role6_2_1_
        from
            cart cart0_
        left outer join
            member member1_
                on cart0_.member_id=member1_.member_id
        where
            cart0_.cart_id=?

        * */

        assertEquals(saveCart.getMember().getId(), member.getId());
    }

}

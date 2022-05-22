package com.shop.service;

import com.shop.domain.constant.ItemSellStatus;
import com.shop.domain.constant.OrderStatus;
import com.shop.domain.dto.OrderDto;
import com.shop.domain.entity.Item;
import com.shop.domain.entity.Member;
import com.shop.domain.entity.Order;
import com.shop.domain.entity.OrderItem;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties.bk")
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    public Item saveItem() {
        Item item = Item.builder().
                itemNm("테스트 상품").
                price(10000).
                itemDetail("테스트 상품 상세 설명").
                itemSellStatus(ItemSellStatus.SELL).
                stockNumber(100).build();
        return itemRepository.save(item);
    }

    public Member saveMember() {
        Member member = new Member();
        member.setEmail("test@test.com");
        return memberRepository.save(member);
    }

    @Test
    @DisplayName("주문 테스트")
    public void order() {
        Item item = saveItem();
        Member member = saveMember();

        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10); // 주문 수량
        orderDto.setItemId(item.getId()); // 주문 상품

        Long orderId = orderService.order(orderDto, member.getEmail()); // 생성된 주문 번호 set

        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new); // 저장된 주문 번호 조회

        List<OrderItem> orderItemList = order.getOrderItems();

        int totalPrice = orderDto.getCount() * item.getPrice(); // 주문 상품 총 가격

        assertEquals(totalPrice, order.getTotalPrice()); // 주문 상품 총 가격과 데이터베이스에 저장된 상품 가격 대조
    }

    @Test
    @DisplayName("주문 취소 테스트")
    public void cancelOrder() {
        Item item = saveItem();
        Member member = saveMember();

        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());
        Long orderId = orderService.order(orderDto, member.getEmail());

        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        orderService.cancelOrder(orderId);

        assertEquals(OrderStatus.CANCEL, order.getOrderStatus());
        assertEquals(100, item.getStockNumber());
        System.out.println("");
    }

}
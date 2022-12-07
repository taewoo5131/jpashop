package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void 상품주문() {
       // given
        Member member = createMember("taewoo");

        Item book = createBook("객체지향의 오해와 진실", 10000, 10);

        // when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        Assertions.assertEquals(OrderStatus.ORDER , getOrder.getStatus(),"상품 주문시 상태는 ORDER " );
        Assertions.assertEquals(1 , getOrder.getOrderItems().size(),"주문한 상품 종류 수가 정확해야 한다 " );
        Assertions.assertEquals(10000 * orderCount , getOrder.getTotalPrice(),"주문 가격은 가격 * 수량이다" );
        Assertions.assertEquals(8, book.getStockQuantity(),"주문 수량만큼 재고가 줄어야 한다" );
    }

    @Test
    void 재고수량초과() {
        // given
        Member member = createMember("taewoo");
        Item book = createBook("객체지향의 오해와 진실", 10000, 10);
        int orderCount = 11;
        // when
        try {
            orderService.order(member.getId(), book.getId(), orderCount);
        } catch (NotEnoughStockException e) {
            return;
        }

        // then
        Assertions.fail("재고 수량 예외가 발생해야 한다.");
    }

    @Test
    void 주문취소() {
        // given
        Member member = createMember("taewoo");
        Item book = createBook("JPA 책 ", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        // when
        orderService.cancelOrder(orderId);

        // then
        Order findOrder = orderRepository.findOne(orderId);
        Assertions.assertEquals(OrderStatus.CANCEL , findOrder.getStatus(),"주문 취소시 상태는 CANCEL");
        Assertions.assertEquals(10 , book.getStockQuantity(),"주문 취소된 상품만큼 재고가 증가해야한다");
    }


    private Item createBook(String name, int price, int stockQuantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember(String name) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(new Address("서울" , "신림" , "123-1"));
        em.persist(member);
        return member;
    }
}
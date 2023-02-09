package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.doInit1();
    }

}

@Component
@Transactional
@RequiredArgsConstructor
class InitService {
    private final EntityManager em;

    public void doInit1() {
        Member member = createMember("userA" , "서울", "123", "45");
        em.persist(member);

        Book book1 = createBook("JPA 1", 10000, 100);
        em.persist(book1);

        Book book2 = createBook("JPA 2", 20000, 200);
        em.persist(book2);

        OrderItem orderItem1 = createOrderItem(book1, 10000, 1);
        OrderItem orderItem2 = createOrderItem(book2, 20000, 2);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);

        em.persist(order);
    }

    public void doInit2() {
        Member member = createMember("userB" , "부산", "456", "78");
        em.persist(member);

        Book book1 = createBook("Spring 1", 30000, 50);
        em.persist(book1);

        Book book2 = createBook("Spring 2", 50000, 150);
        em.persist(book2);

        OrderItem orderItem1 = createOrderItem(book1, 30000, 3);
        OrderItem orderItem2 = createOrderItem(book2, 50000, 4);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);

        em.persist(order);
    }

    private OrderItem createOrderItem(Book book1, int i, int i2) {
        return OrderItem.createOrderItem(book1, i, i2);
    }

    private Book createBook(String s, int i, int i2) {
        Book book1 = new Book();
        book1.setName(s);
        book1.setPrice(i);
        book1.setStockQuantity(i2);
        return book1;
    }

    private Member createMember(String userName , String city , String street , String zipcode) {
        Member member = new Member();
        member.setName(userName);
        member.setAddress(new Address(city , street , zipcode));
        return member;
    }
}

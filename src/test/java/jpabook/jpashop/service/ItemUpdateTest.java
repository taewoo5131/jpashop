package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@SpringBootTest
@Transactional
public class ItemUpdateTest {

    @PersistenceContext
    EntityManager em;

    @Test
    @Rollback(value = false)
    void persist() {
        Book book = new Book();
        book.setName("test");
        book.setAuthor("taewoo");
        book.setIsbn("123-123");
        book.setPrice(20000);
        book.setStockQuantity(10);
        em.persist(book);
    }

    @Test
    @Rollback(value = false)
    void dirtyCheckTest() {
        Book book = em.find(Book.class, 3L);
        book.setName("test update");
    }

    @Test
    @Rollback(value = false)
    void mergeTest() {
        Book book = new Book();
        book.setId(3L);
        book.setName("test merge");
        em.merge(book);
    }
}

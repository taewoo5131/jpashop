package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Slf4j
@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        Member findMember = em.find(Member.class, id);
        return findMember;
    }

    public List<Member> findAll() {
        List<Member> findAllMember = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        return findAllMember;
    }

    public List<Member> findByName(String name) {
        List<Member> findAllMemberByName = em.createQuery("select m from Member m where m.name =: name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return findAllMemberByName;
    }
}

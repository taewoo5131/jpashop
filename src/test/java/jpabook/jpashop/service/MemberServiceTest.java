package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("taewoo");

        // when
        memberService.join(member);

        // then
        Assertions.assertEquals(member , memberRepository.findOne(member.getId()));
    }

    @Test
    void 중복회원예외() {
        // given
        Member member = new Member();
        member.setName("taewoo");
        memberService.join(member);

        // when
        try {
            Member member2 = new Member();
            member2.setName("taewoo");
            memberService.join(member2);
        } catch (IllegalStateException e) {
            return;
        }

        // then
        Assertions.fail("예외가 발생해야 한다.");

    }

}
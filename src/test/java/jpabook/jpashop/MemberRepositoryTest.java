package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    void 테스트() {
        // given
        Member member = new Member();
        member.setUsername("테스트용");
        // when
        memberRepository.save(member);
        Member member1 = memberRepository.find(member.getId());
        //then
        Assertions.assertThat(member1.getUsername()).isEqualTo("테스트용");
    }

}
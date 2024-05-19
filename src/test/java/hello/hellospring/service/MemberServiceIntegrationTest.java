package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional // 트랜잭션 실행 후 롤백을 해준다
public class MemberServiceIntegrationTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void findMembers() {
        Member member = new Member();
        member.setName("max");
        memberRepository.save(member);

        List<Member> members = memberService.findMembers();
        System.out.println(members);

        assertThat(members.size()).isEqualTo(members.size());
    }

    @Test
    public void findOne() {
        Optional<Member> findMember = memberService.findOne(1L);
        System.out.println(findMember);

        assertThat(findMember).isNotNull();
    }

    @Test
    public void findByName() {
        Optional<Member> findMember = memberService.findByName("max");
        System.out.println(findMember);

        assertThat(findMember).isNotEmpty();
    }

    @Test
    public void join() {
        Member member = new Member();
        member.setName("max22");

        Long saveId = memberService.join(member);
        System.out.println(saveId);

        Optional<Member> findMember = Optional.of(memberService.findOne(saveId).get());
        assertThat(member.getName()).isEqualTo(findMember.get().getName());
    }

    @Test
    public void duplicateMemberNotAvailable() {
        Member member = new Member();
        member.setName("max3");

        memberService.join(member);

//        try {
//            memberService.join(member);
//            fail("Should throw exception");
//        } catch (IllegalStateException e) {
//            assertThat(e.getMessage()).contains("Already Existing Member.");
//        }

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member));
        assertThat(e.getMessage()).isEqualTo("Already Existing Member.");
    }
}

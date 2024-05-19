package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.*;

public class MemberServiceTest {
    MemoryMemberRepository memberRepository;
    MemberService memberService;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    public void findMembers() {
        Member member = new Member();
        member.setName("max");
        memberRepository.save(member);

        List<Member> members = memberService.findMembers();
        System.out.println(members);

        assertThat(members.size()).isEqualTo(1);
    }

    @Test
    public void findOne() {
        Member member = new Member();
        member.setId(1L);
        member.setName("max");
        memberRepository.save(member);

        Optional<Member> findMember = memberService.findOne(1L);
        System.out.println(findMember);

        assertThat(findMember).isNotNull();
    }

    @Test
    public void join() {
        Member member = new Member();
        member.setName("max");

        Long saveId = memberService.join(member);
        System.out.println(saveId);

        Optional<Member> findMember = Optional.of(memberService.findOne(saveId).get());
        assertThat(member.getName()).isEqualTo(findMember.get().getName());
    }

    @Test
    public void duplicateMemberNotAvailable() {
        Member member = new Member();
        member.setName("max");

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

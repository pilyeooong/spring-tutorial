package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");
        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        String name1 = "max1";
        String name2 = "max2";

        Member member1 = new Member();
        member1.setName(name1);
        repository.save(member1);

        Member member2 = new Member();
        member2.setName(name2);
        repository.save(member2);

        Member result = repository.findByName(name1).get();
        System.out.println(result);
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        String name1 = "max1";
        String name2 = "max2";

        Member member1 = new Member();
        member1.setName(name1);
        repository.save(member1);

        Member member2 = new Member();
        member2.setName(name2);
        repository.save(member2);

        List<Member> result = repository.findAll();
        System.out.println(result);
        assertThat(result).size().isEqualTo(2);
    }
}

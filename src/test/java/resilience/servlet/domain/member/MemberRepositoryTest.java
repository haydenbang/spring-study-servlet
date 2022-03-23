package resilience.servlet.domain.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Member member = new Member("user1", 20);

        //when
        Member saveMember = memberRepository.save(member);

        //then
        Member findMember = memberRepository.findById(saveMember.getId());
        assertThat(findMember).isEqualTo(saveMember);
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
        Member member1 = new Member("user2", 20);
        Member member2 = new Member("user2", 40);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> users = memberRepository.findAll();
        assertThat(users.size()).isEqualTo(2);
        assertThat(users).contains(member1, member2);
    }
}
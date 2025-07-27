package project.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    // Optional helper if needed later
    default Optional<String> getRefreshTokenByEmail(String email) {
        return findByEmail(email).map(Member::getRefreshToken);
    }
}

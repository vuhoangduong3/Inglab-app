package unicorns.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicorns.backend.entity.TokenBlacklist;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, String> {
    boolean existsByToken(String token);
}
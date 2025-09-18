package unicorns.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicorns.backend.entity.User;

import java.util.List;
import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(long userid);
    List<User> findByRoleAndNameContainingIgnoreCase(String role, String name);
}

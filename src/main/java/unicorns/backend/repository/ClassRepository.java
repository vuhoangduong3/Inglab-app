package unicorns.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicorns.backend.entity.Classes;

public interface ClassRepository extends JpaRepository<Classes, Long> {}

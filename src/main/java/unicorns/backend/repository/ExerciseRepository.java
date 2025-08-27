package unicorns.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicorns.backend.entity.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}

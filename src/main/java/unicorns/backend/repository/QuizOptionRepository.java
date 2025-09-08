package unicorns.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicorns.backend.entity.QuizOption;

public interface QuizOptionRepository extends JpaRepository<QuizOption,Long> {
}

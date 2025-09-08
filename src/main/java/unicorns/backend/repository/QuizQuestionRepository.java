package unicorns.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicorns.backend.entity.QuizQuestion;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion,Long> {
}

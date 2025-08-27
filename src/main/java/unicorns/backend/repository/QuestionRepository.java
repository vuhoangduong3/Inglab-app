// QuestionRepository.java
package unicorns.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicorns.backend.entity.Question;
import unicorns.backend.entity.QuestionResult;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findById(Long Id);
}

// QuestionResultRepository.java
package unicorns.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicorns.backend.entity.QuestionResult;

import java.util.Optional;

@Repository
public interface QuestionResultRepository extends JpaRepository<QuestionResult, Long> {
    Optional<QuestionResult> findById(Long Id);
}

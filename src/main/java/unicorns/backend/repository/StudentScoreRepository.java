package unicorns.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicorns.backend.entity.StudentScore;

import java.util.List;

public interface StudentScoreRepository extends JpaRepository<StudentScore, Long> {
    List<StudentScore> findByExercise_IdAndClassEntity_Id(Long exerciseId, Long classId);
    StudentScore findByStudent_IdAndExercise_Id(Long studentId, Long exerciseId);
}

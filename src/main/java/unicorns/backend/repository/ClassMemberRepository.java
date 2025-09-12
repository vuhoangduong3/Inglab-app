package unicorns.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import unicorns.backend.entity.ClassMember;
import unicorns.backend.entity.Classes;

import java.util.List;
import java.util.Optional;

public interface ClassMemberRepository extends JpaRepository<ClassMember, Long> {
    List<ClassMember> findByClassEntity_Id(Long classId);
    List<ClassMember> findByStudent_Id(Long studentId);
    boolean existsByClassEntityIdAndStudentId(Long classId, Long studentId);
    Optional<Object> findByClassEntityIdAndStudentId(Long classId, Long studentId);

    @Query("SELECT cm.classEntity FROM ClassMember cm WHERE cm.student.id = :studentId")
    List<Classes> findClassesByStudentId(Long studentId);
}

package unicorns.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicorns.backend.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByClassEntity_Id(Long classId);
    List<Schedule> findByTeacher_Id(Long teacherId);
    List<Schedule> findByTeacherId(Long teacherId);
    List<Schedule> findByTeacherIdAndDate(Long teacherId, LocalDate date);
}

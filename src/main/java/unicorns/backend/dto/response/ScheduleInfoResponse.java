package unicorns.backend.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import unicorns.backend.entity.User;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleInfoResponse {
    Long scheduleId;
    Long classId;
    String className;
    LocalDate date;
    LocalTime startTime;
    LocalTime endTime;
    String room;
    String teacherName;
}

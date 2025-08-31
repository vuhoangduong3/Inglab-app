package unicorns.backend.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateScheduleRequest {
    Long classId;
    Long teacherId;
    LocalDate date;
    LocalTime startTime;
    LocalTime endTime;
    String room;
}

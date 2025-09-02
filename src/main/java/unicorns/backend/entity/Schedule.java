package unicorns.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import unicorns.backend.util.Schema;

import java.time.LocalDate;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = Schema.BACKEND_APP)
public class Schedule extends BaseEntity{
    LocalDate date;
    LocalTime startTime;
    LocalTime endTime;
    String room;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    @JsonBackReference
    Classes classEntity;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    User teacher;
}

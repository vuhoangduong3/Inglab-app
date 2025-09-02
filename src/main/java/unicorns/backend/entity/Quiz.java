package unicorns.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import unicorns.backend.util.Schema;

import java.util.List;
//tam thoi

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "quiz", schema = Schema.BACKEND_APP)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Quiz extends BaseEntity {
    String title;
    String description;
}

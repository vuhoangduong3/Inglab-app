package unicorns.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import unicorns.backend.util.Schema;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = Schema.BACKEND_APP)

public class ClassMember extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    @JsonBackReference
    Classes classEntity;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    User student;
}

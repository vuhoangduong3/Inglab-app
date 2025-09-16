package unicorns.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import unicorns.backend.util.Schema;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(schema = Schema.BACKEND_APP)

public class StudentScore extends BaseEntity {
    @ManyToOne
    private User student;

    @ManyToOne
    private Exercise exercise;

    @ManyToOne
    private Classes classEntity;

    private Integer totalQuestion;

    private Integer correctAnswer;

}

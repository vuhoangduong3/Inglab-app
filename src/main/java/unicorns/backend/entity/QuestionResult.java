package unicorns.backend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import unicorns.backend.util.Schema;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "question_result", schema = Schema.BACKEND_APP)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionResult extends BaseEntity {

    @OneToOne(optional = false)
    @JoinColumn(name = "question_id", nullable = false, unique = true)
    Question question;
    String result;
}

package unicorns.backend.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question_quiz", schema = "backendapp")
public class QuizQuestion extends BaseEntity {

    @Column(length = 1000)
    private String text;


    @ManyToOne(optional = false)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;


    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizOption> options = new ArrayList<>();


    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public Exercise getExercise() { return exercise; }
    public void setExercise(Exercise exercise) { this.exercise = exercise; }

    public List<QuizOption> getOptions() { return options; }
    public void setOptions(List<QuizOption> options) { this.options = options; }
}

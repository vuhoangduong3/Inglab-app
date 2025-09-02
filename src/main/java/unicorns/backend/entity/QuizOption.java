package unicorns.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "option_quiz", schema = "backendapp")
public class QuizOption extends BaseEntity {

    @Column(length = 1000)
    private String text;

    private boolean correct;

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id")
    private QuizQuestion question;

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public boolean isCorrect() { return correct; }
    public void setCorrect(boolean correct) { this.correct = correct; }

    public QuizQuestion getQuestion() { return question; }
    public void setQuestion(QuizQuestion question) { this.question = question; }
}

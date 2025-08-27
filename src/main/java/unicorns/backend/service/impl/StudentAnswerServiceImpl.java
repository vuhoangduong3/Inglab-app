package unicorns.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicorns.backend.dto.request.StudentAnswerRequest;
import unicorns.backend.dto.response.StudentAnswerResponse;
import unicorns.backend.entity.*;
import unicorns.backend.repository.*;
import unicorns.backend.service.StudentAnswerService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentAnswerServiceImpl implements StudentAnswerService {

    private final StudentAnswerRepository studentAnswerRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuestionResultRepository questionResultRepository;

    @Override
    public List<StudentAnswerResponse> submitQuiz(Long studentId, Long quizId, List<StudentAnswerRequest> requests) {

        User student = userRepository.findById(studentId).orElse(null);
        Quiz quiz = quizRepository.findById(quizId).orElse(null);

        List<StudentAnswer> answers = requests.stream().map(req -> {
            Question question = questionRepository.findById(req.getQuestionId()).orElse(null);

            // lấy đáp án đúng từ QuestionResult
            QuestionResult result = questionResultRepository.findById(req.getQuestionId()).orElse(null);
            boolean correct = result != null && result.getResult().equals(req.getStudentAnswer());

            StudentAnswer studentAnswer = new StudentAnswer();
            studentAnswer.setStudent(student);
            studentAnswer.setQuiz(quiz);
            studentAnswer.setQuestion(question);
            studentAnswer.setStudentAnswer(req.getStudentAnswer());
            studentAnswer.setIsCorrect(correct);

            return studentAnswerRepository.save(studentAnswer);
        }).collect(Collectors.toList());

        return answers.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<StudentAnswerResponse> getAnswer(Long studentId, Long quizId) {
        List<StudentAnswer> answers = studentAnswerRepository.findByStudentIdAndQuizId(studentId, quizId);
        return answers.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private StudentAnswerResponse mapToResponse(StudentAnswer answer) {
        StudentAnswerResponse res = new StudentAnswerResponse();
        res.setId(answer.getId());
        res.setStudentId(answer.getStudent().getId());
        res.setStudentName(answer.getStudent().getName());
        res.setQuizId(answer.getQuiz().getId());
        res.setQuestionId(answer.getQuestion().getId());
        res.setQuestionText(answer.getQuestion().getQuestion());
        res.setStudentAnswer(answer.getStudentAnswer());
        res.setIsCorrect(answer.getIsCorrect());
        return res;
    }
}

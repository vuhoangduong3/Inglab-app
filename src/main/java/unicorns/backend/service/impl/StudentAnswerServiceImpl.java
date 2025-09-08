package unicorns.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicorns.backend.dto.request.StudentAnswerRequest;
import unicorns.backend.dto.response.ExerciseAnswerResponse;
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
    private final ExerciseRepository exerciseRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizOptionRepository quizOptionRepository;

    @Override
    public ExerciseAnswerResponse submitQuiz(Long studentId, Long exerciseId, List<StudentAnswerRequest> requests) {

        User student = userRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        Exercise exercise = exerciseRepository.findById(exerciseId).orElseThrow(() -> new RuntimeException("Exercise not found"));

        List<StudentAnswer> answers = requests.stream().map(req -> {
            QuizQuestion question = quizQuestionRepository.findById(req.getQuizQuestionId()).orElse(null);

            QuizOption option = quizOptionRepository.findById(req.getQuizOptionId()).orElse(null);
            boolean correct = option != null && option.isCorrect();

            StudentAnswer studentAnswer = new StudentAnswer();
            studentAnswer.setStudent(student);
            studentAnswer.setExercise(exercise);
            studentAnswer.setQuestion(question);
            studentAnswer.setOption(option);
            studentAnswer.setIsCorrect(correct);

            return studentAnswerRepository.save(studentAnswer);
        }).collect(Collectors.toList());

        return mapToExerciseResponse(student, exercise, answers);
    }

    @Override
    public ExerciseAnswerResponse getAnswer(Long studentId, Long exerciseId) {
            List<StudentAnswer> answers = studentAnswerRepository.findByStudent_IdAndExercise_Id(studentId, exerciseId);
        User student = userRepository.findById(studentId).orElse(null);
        Exercise exercise = exerciseRepository.findById(exerciseId).orElse(null);
        return mapToExerciseResponse(student, exercise, answers);
    }

    private StudentAnswerResponse mapToResponse(StudentAnswer answer) {
        StudentAnswerResponse res = new StudentAnswerResponse();
        res.setId(answer.getId());
        res.setStudentId(answer.getStudent().getId());
        res.setStudentName(answer.getStudent().getName());
        res.setExerciseId(answer.getExercise().getId());
        res.setQuizQuestionId(answer.getQuestion().getId());
        res.setQuizOptionId(answer.getOption().getId());
        res.setIsCorrect(answer.getIsCorrect());
        return res;
    }

    private ExerciseAnswerResponse mapToExerciseResponse(User student, Exercise exercise, List<StudentAnswer> answers) {
        ExerciseAnswerResponse res = new ExerciseAnswerResponse();
        res.setStudentId(student.getId());
        res.setStudentName(student.getName());
        res.setExerciseId(exercise.getId());
        res.setTotalQuestions(answers.size());
        res.setCorrectAnswers((int) answers.stream().filter(StudentAnswer::getIsCorrect).count());
        res.setAnswers(answers.stream().map(this::mapToResponse).collect(Collectors.toList()));
        return res;
    }
}

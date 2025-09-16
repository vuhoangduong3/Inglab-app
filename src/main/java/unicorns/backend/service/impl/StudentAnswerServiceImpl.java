package unicorns.backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicorns.backend.dto.request.StudentAnswerRequest;
import unicorns.backend.dto.response.AllStudentScoreResponse;
import unicorns.backend.dto.response.ExerciseAnswerResponse;
import unicorns.backend.dto.response.StudentAnswerResponse;
import unicorns.backend.dto.response.StudentScoreResponse;
import unicorns.backend.entity.*;
import unicorns.backend.repository.*;
import unicorns.backend.service.StudentAnswerService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentAnswerServiceImpl implements StudentAnswerService {

    private final StudentAnswerRepository studentAnswerRepository;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizOptionRepository quizOptionRepository;
    private final StudentScoreRepository studentScoreRepository;
    private final ClassMemberRepository classMemberRepository;

    @Override
    public ExerciseAnswerResponse submitQuiz(Long studentId, Long exerciseId, List<StudentAnswerRequest> requests) {

        User student = userRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        Exercise exercise = exerciseRepository.findById(exerciseId).orElseThrow(() -> new RuntimeException("Exercise not found"));

        // Xoá hết answer cũ trước khi lưu mới
        studentAnswerRepository.deleteByStudent_IdAndExercise_Id(studentId, exerciseId);

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

        int correctCount = (int) answers.stream().filter(StudentAnswer::getIsCorrect).count();

        StudentScore score = studentScoreRepository.findByStudent_IdAndExercise_Id(studentId, exerciseId);
        if (score == null) {
            score = new StudentScore();
            score.setStudent(student);
            score.setExercise(exercise);
        }

        if (score.getCorrectAnswer() == null || correctCount > score.getCorrectAnswer()) {
            score.setCorrectAnswer(correctCount);
            score.setTotalQuestion(answers.size());
            studentScoreRepository.save(score);
        }

        return mapToExerciseResponse(student, exercise, answers);
    }

    @Override
    public ExerciseAnswerResponse getAnswer(Long studentId, Long exerciseId) {
            List<StudentAnswer> answers = studentAnswerRepository.findByStudent_IdAndExercise_Id(studentId, exerciseId);
        User student = userRepository.findById(studentId).orElse(null);
        Exercise exercise = exerciseRepository.findById(exerciseId).orElse(null);
        return mapToExerciseResponse(student, exercise, answers);
    }

    @Override
    public AllStudentScoreResponse getAllStudentScore(Long classId, Long exerciseId) {
        List<ClassMember> members = classMemberRepository.findByClassEntity_Id(classId);

        List<StudentScoreResponse> scoreResponses = members.stream().map(member -> {
            User student = member.getStudent();
            StudentScore score = studentScoreRepository.findByStudent_IdAndExercise_Id(student.getId(), exerciseId);

            StudentScoreResponse res = new StudentScoreResponse();
            res.setId(score != null ? score.getId() : 0);
            res.setStudentId(student.getId());
            res.setStudentName(student.getName());
            res.setCorrectAnswer(score != null ? score.getCorrectAnswer() : 0);
            return res;
        }).collect(Collectors.toList());

        AllStudentScoreResponse response = new AllStudentScoreResponse();
        response.setClassId(classId);
        response.setExerciseId(exerciseId);
        response.setAnswers(scoreResponses);

        return response;
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

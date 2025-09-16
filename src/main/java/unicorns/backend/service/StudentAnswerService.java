package unicorns.backend.service;

import unicorns.backend.dto.request.StudentAnswerRequest;
import unicorns.backend.dto.response.AllStudentScoreResponse;
import unicorns.backend.dto.response.ExerciseAnswerResponse;
import unicorns.backend.dto.response.StudentAnswerResponse;

import java.util.List;

public interface StudentAnswerService {
    ExerciseAnswerResponse submitQuiz(Long studentId, Long exerciseId, List<StudentAnswerRequest> requests);
    ExerciseAnswerResponse getAnswer(Long studentId, Long exerciseId);
    AllStudentScoreResponse getAllStudentScore(Long classId, Long exerciseId);
}


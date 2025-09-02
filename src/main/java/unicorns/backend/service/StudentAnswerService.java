package unicorns.backend.service;

import unicorns.backend.dto.request.StudentAnswerRequest;
import unicorns.backend.dto.response.StudentAnswerResponse;

import java.util.List;

public interface StudentAnswerService {
    List<StudentAnswerResponse> submitQuiz(Long studentId, Long quizId, List<StudentAnswerRequest> requests);
    List<StudentAnswerResponse> getAnswer(Long studentId, Long quizId);
}

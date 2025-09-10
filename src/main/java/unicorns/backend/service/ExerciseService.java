package unicorns.backend.service;

import unicorns.backend.dto.request.ExerciseCreateRequest;
import unicorns.backend.dto.response.ExerciseDetailResponse;
import unicorns.backend.dto.response.ExerciseMetaResponse;

import java.util.List;

public interface ExerciseService {
    ExerciseMetaResponse create(ExerciseCreateRequest req);
    List<ExerciseMetaResponse> listAllMetas();
    ExerciseDetailResponse getDetail(Long id);
}

package unicorns.backend.service;

import unicorns.backend.dto.request.BaseRequest;
import unicorns.backend.dto.request.ExerciseCreateRequest;
import unicorns.backend.dto.response.BaseResponse;
import unicorns.backend.dto.response.ExerciseDetailResponse;
import unicorns.backend.dto.response.ExerciseMetaResponse;

import java.util.List;

public interface ExerciseService {
    BaseResponse<ExerciseMetaResponse> create(ExerciseCreateRequest req);
    BaseResponse<List<ExerciseMetaResponse>> listAllMetas();
    BaseResponse<ExerciseDetailResponse> getDetail(Long id);
}

package unicorns.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import unicorns.backend.dto.request.BaseRequest;
import unicorns.backend.dto.request.StudentAnswerRequest;
import unicorns.backend.dto.response.AllStudentScoreResponse;
import unicorns.backend.dto.response.BaseResponse;
import unicorns.backend.dto.response.ExerciseAnswerResponse;
import unicorns.backend.dto.response.StudentAnswerResponse;
import unicorns.backend.service.StudentAnswerService;
import unicorns.backend.util.ApplicationCode;
import unicorns.backend.util.Const;

import java.util.List;

@RestController
@RequestMapping(Const.PREFIX_STUDENTANSWER_V1)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StudentAnswerController {

    private final StudentAnswerService studentAnswerService;

    @Operation(summary = "Submit exercise", description = "Submit multiple answers for a exercise by a student.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Answers submitted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @PostMapping("submit/{exerciseId}/student/{studentId}")
    public BaseResponse<ExerciseAnswerResponse> submitQuiz(
            @PathVariable Long exerciseId,
            @PathVariable Long studentId,
            @Valid @RequestBody List<StudentAnswerRequest> request
    ) {
        ExerciseAnswerResponse response =
                studentAnswerService.submitQuiz(studentId, exerciseId, request);

        BaseResponse<ExerciseAnswerResponse> baseResponse =
                new BaseResponse<>(ApplicationCode.SUCCESS);
        baseResponse.setWsResponse(response);
        return baseResponse;
    }

    @Operation(summary = "Get student answers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Answers retrieved successfully")
    })
    @GetMapping("getAnswers/{exerciseId}/student/{studentId}")
    public BaseResponse<ExerciseAnswerResponse> getAnswers(
            @PathVariable Long exerciseId,
            @PathVariable Long studentId
    ) {
        ExerciseAnswerResponse response =
                studentAnswerService.getAnswer(studentId, exerciseId);

        BaseResponse<ExerciseAnswerResponse> baseResponse =
                new BaseResponse<>(ApplicationCode.SUCCESS);
        baseResponse.setWsResponse(response);
        return baseResponse;
    }

    @Operation (summary = "Get all point of class")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Answers retrieved successfully")
    })
    @GetMapping("getAnswers/{exerciseId}/class/{classId}")
    public BaseResponse<AllStudentScoreResponse> getAllAnswers (
        @PathVariable Long classId,
        @PathVariable Long exerciseId
    ) {
        AllStudentScoreResponse response =
                studentAnswerService.getAllStudentScore(classId, exerciseId);

        BaseResponse<AllStudentScoreResponse> baseResponse =
                new BaseResponse<>(ApplicationCode.SUCCESS);
        baseResponse.setWsResponse(response);
        return baseResponse;
    }
}

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
import unicorns.backend.dto.response.BaseResponse;
import unicorns.backend.dto.response.StudentAnswerResponse;
import unicorns.backend.service.StudentAnswerService;
import unicorns.backend.util.ApplicationCode;
import unicorns.backend.util.Const;

import java.util.List;

@RestController
@RequestMapping(Const.PREFIX_STUDENTANSWER_V1) // ví dụ: "/api/v1/student-answer/"
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StudentAnswerController {

    StudentAnswerService studentAnswerService;

    @Operation(summary = "Submit quiz answers", description = "Submit multiple answers for a quiz by a student.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Answers submitted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @PostMapping("submit/{quizId}/student/{studentId}")
    public BaseResponse<List<StudentAnswerResponse>> submitQuiz(
            @PathVariable Long quizId,
            @PathVariable Long studentId,
            @Valid @RequestBody BaseRequest<List<StudentAnswerRequest>> request
    ) {
        List<StudentAnswerResponse> responses =
                studentAnswerService.submitQuiz(studentId, quizId, request.getWsRequest());
        BaseResponse<List<StudentAnswerResponse>> baseResponse =
                new BaseResponse<>(ApplicationCode.SUCCESS);
        baseResponse.setWsResponse(responses);
        return baseResponse;
    }

    @Operation(summary = "Get student answers", description = "Retrieve submitted answers of a student for a quiz.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Answers retrieved successfully")
    })
    @GetMapping("getAnswers/{quizId}/student/{studentId}")
    public BaseResponse<List<StudentAnswerResponse>> getAnswers(
            @PathVariable Long quizId,
            @PathVariable Long studentId
    ) {
        List<StudentAnswerResponse> responses =
                studentAnswerService.getAnswer(studentId, quizId);
        BaseResponse<List<StudentAnswerResponse>> baseResponse =
                new BaseResponse<>(ApplicationCode.SUCCESS);
        baseResponse.setWsResponse(responses);
        return baseResponse;
    }
}

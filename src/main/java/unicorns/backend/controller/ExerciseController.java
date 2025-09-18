package unicorns.backend.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import unicorns.backend.dto.request.ExerciseCreateRequest;
import unicorns.backend.dto.response.BaseResponse;
import unicorns.backend.dto.response.ExerciseDetailResponse;
import unicorns.backend.dto.response.ExerciseMetaResponse;
import unicorns.backend.service.ExerciseService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService service;

    public ExerciseController(ExerciseService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new exercise")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @PostMapping
    public BaseResponse<ExerciseMetaResponse> create(@Valid @RequestBody ExerciseCreateRequest req) {
        return service.create(req);
    }

    @Operation(summary = "List all exercises (meta)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse( responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @GetMapping
    public BaseResponse<List<ExerciseMetaResponse>> list() {
        return service.listAllMetas();
    }

    @Operation(summary = "Get one exercise (full detail) by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @GetMapping("/{id}")
    public BaseResponse<ExerciseDetailResponse> getDetail(@PathVariable Long id) {
        return service.getDetail(id);
    }
}

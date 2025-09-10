package unicorns.backend.controller;

import unicorns.backend.dto.request.ExerciseCreateRequest;
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
    @PostMapping
    public ExerciseMetaResponse create(@Valid @RequestBody ExerciseCreateRequest req) {
        return service.create(req);
    }

    @Operation(summary = "Get list of all exercises")
    @GetMapping
    public List<ExerciseMetaResponse> list() {
        return service.listAllMetas();
    }

    @Operation(summary = "Get all")
    @GetMapping("/{id}")
    public ExerciseDetailResponse getDetail(@PathVariable Long id) {  return service.getDetail(id); }
}

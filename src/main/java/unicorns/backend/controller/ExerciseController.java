package unicorns.backend.controller;

import unicorns.backend.dto.request.ExerciseCreateRequest;
import unicorns.backend.dto.response.ExerciseMetaResponse;
import unicorns.backend.service.ExerciseService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService service;

    public ExerciseController(ExerciseService service) {
        this.service = service;
    }

    @PostMapping
    public ExerciseMetaResponse create(@Valid @RequestBody ExerciseCreateRequest req) {
        return service.create(req);
    }

    @GetMapping
    public List<ExerciseMetaResponse> list() {
        return service.listAllMetas();
    }
}

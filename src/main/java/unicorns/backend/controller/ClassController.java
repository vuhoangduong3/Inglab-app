package unicorns.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import unicorns.backend.dto.request.AddStudentToClassRequest;
import unicorns.backend.dto.request.CreateClassRequest;
import unicorns.backend.dto.request.RemoveStudentsFromClassRequest;
import unicorns.backend.dto.response.*;
import unicorns.backend.service.ClassService;
import unicorns.backend.util.Const;

import java.util.List;

@RestController
@RequestMapping(Const.PREFIX_CLASS_V1)
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    @Operation(summary = "get all classes")
    @GetMapping("/getAllClasses")
    public BaseResponse<List<GetClassResponse>> getAllClasses() {
        return classService.getAllClasses();
    }

    @Operation(summary = "create class")
    @PostMapping("/CreateClass")
    public BaseResponse<GetClassResponse> createClass(@RequestBody CreateClassRequest request) {
        return classService.createClass(request);
    }

    @Operation(summary = "delete class")
    @DeleteMapping("/deleteClass/{id}")
    public BaseResponse<DeleteClassResponse> deleteClass(@PathVariable long id){
        return classService.deleteClass(id);
    }

    @Operation(summary = "add student to class ")
    @PostMapping("/{id}/members")
    public BaseResponse<AddStudentToClassResponse> addStudentsToClass(
            @PathVariable("id") Long classId,
            @RequestBody AddStudentToClassRequest request) {
        return classService.addStudentsToClass(classId, request);
    }

    @Operation(summary = "delete member from class")
    @DeleteMapping("/{id}/members")
    public BaseResponse<RemoveStudentsFromClassResponse> removeStudentsFromClass(
            @PathVariable("id") Long classId,
            @RequestBody RemoveStudentsFromClassRequest request) {
        return classService.removeStudentsFromClass(classId, request);
    }
    @Operation(summary = "get class by id")
    @GetMapping("/{id}")
    public BaseResponse<GetClassByIdResponse> getClassById(@PathVariable Long id) {
        return classService.getClassById(id);
    }
}
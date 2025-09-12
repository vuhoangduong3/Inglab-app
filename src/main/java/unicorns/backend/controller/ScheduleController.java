package unicorns.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import unicorns.backend.dto.request.CreateScheduleRequest;
import unicorns.backend.dto.request.ScheduleByDateRequest;
import unicorns.backend.dto.response.BaseResponse;
import unicorns.backend.dto.response.CreateScheduleResponse;
import unicorns.backend.dto.response.ScheduleInfoResponse;
import unicorns.backend.service.ScheduleService;
import unicorns.backend.util.Const;

import java.util.List;

@RestController
@RequestMapping(Const.PREFIX_SCHEDULE_V1)
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(summary = "createSchedule")
    @PostMapping("/createSchedule")
    public BaseResponse<CreateScheduleResponse> createSchedule(@RequestBody CreateScheduleRequest request) {
        return scheduleService.createSchedule(request);
    }

    @Operation(summary = "Get Student Schedule by id")
    @GetMapping("/student/{id}")
    public BaseResponse<List<ScheduleInfoResponse>> getStudentSchedule(@PathVariable("id") Long studentId) {
        return scheduleService.getStudentSchedule(studentId);
    }

    @Operation(summary = "get teacher schedule by id")
    @GetMapping("/teacher/{id}")
    public BaseResponse<List<ScheduleInfoResponse>> getTeacherSchedule(@PathVariable("id") Long teacherId) {
        return scheduleService.getTeacherSchedule(teacherId);
    }

    @Operation(summary = "get student Schedule by date")
    @GetMapping("/student/{id}/byDate")
    public BaseResponse<List<ScheduleInfoResponse>> getStudentScheduleByDate(@PathVariable("id") long studentId, @RequestBody ScheduleByDateRequest request) {
        return scheduleService.getStudentScheduleByDate(studentId, request);
    }

    @Operation(summary = "get teacher Schedule by date")
    @GetMapping("/teacher/{id}/byDate")
    public BaseResponse<List<ScheduleInfoResponse>> getTeacherScheduleByDate(@PathVariable("id") long teacherId, @RequestBody ScheduleByDateRequest request) {
        return scheduleService.getTeacherScheduleByDate(teacherId, request);
    }
    @Operation(summary = "get current user Schedule")
    @GetMapping("/currentUser")
    public BaseResponse<List<ScheduleInfoResponse>> getCurrentUserSchedule(@RequestHeader("Authorization") String AuthHeader){
        return scheduleService.getCurrentUserResponse(AuthHeader);
    }
}

package unicorns.backend.service;

import org.springframework.web.bind.annotation.RequestHeader;
import unicorns.backend.dto.request.CreateScheduleRequest;
import unicorns.backend.dto.request.ScheduleByDateRequest;
import unicorns.backend.dto.response.BaseResponse;
import unicorns.backend.dto.response.CreateScheduleResponse;
import unicorns.backend.dto.response.ScheduleInfoResponse;
import unicorns.backend.dto.response.StudentInfoResponse;

import java.util.List;

public interface ScheduleService {
    BaseResponse<CreateScheduleResponse> createSchedule(CreateScheduleRequest request);
    BaseResponse<List<ScheduleInfoResponse>> getStudentSchedule(Long studentId);
    BaseResponse<List<ScheduleInfoResponse>> getTeacherSchedule(Long teacherId);
    BaseResponse<List<ScheduleInfoResponse>> getStudentScheduleByDate(Long studentId, ScheduleByDateRequest request);
    BaseResponse<List<ScheduleInfoResponse>> getTeacherScheduleByDate(Long teacherId, ScheduleByDateRequest request);
    BaseResponse<List<ScheduleInfoResponse>> getCurrentUserResponse(@RequestHeader("Authorization") String AuthHeader);
    BaseResponse<List<StudentInfoResponse>> getStudentInSchedule(@RequestHeader("Authorization") String AuthHeader,
                                                                 long ScheduleId);
}

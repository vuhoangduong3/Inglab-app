package unicorns.backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import unicorns.backend.dto.request.CreateScheduleRequest;
import unicorns.backend.dto.request.ScheduleByDateRequest;
import unicorns.backend.dto.response.BaseResponse;
import unicorns.backend.dto.response.CreateScheduleResponse;
import unicorns.backend.dto.response.ScheduleInfoResponse;
import unicorns.backend.entity.Classes;
import unicorns.backend.entity.Schedule;
import unicorns.backend.entity.User;
import unicorns.backend.repository.ClassMemberRepository;
import unicorns.backend.repository.ClassRepository;
import unicorns.backend.repository.ScheduleRepository;
import unicorns.backend.repository.UserRepository;
import unicorns.backend.service.ScheduleService;
import unicorns.backend.util.ApplicationCode;
import unicorns.backend.util.ApplicationException;
import unicorns.backend.util.JwtUtil;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;
    private final ClassMemberRepository classMemberRepository;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public BaseResponse<CreateScheduleResponse> createSchedule(CreateScheduleRequest request) {

        Classes clazz = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new ApplicationException(ApplicationCode.CLASS_NOT_FOUND));

        User teacher = userRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new ApplicationException(ApplicationCode.USER_NOT_FOUND));

        Schedule schedule = new Schedule();
        schedule.setClassEntity(clazz);
        schedule.setTeacher(teacher);
        schedule.setDate(request.getDate());
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        schedule.setRoom(request.getRoom());

        scheduleRepository.save(schedule);

        CreateScheduleResponse response = new CreateScheduleResponse();
        response.setId(schedule.getId());
        response.setClassId(clazz.getId());
        response.setTeacherId(teacher.getId());
        response.setDate(schedule.getDate());
        response.setStartTime(schedule.getStartTime());
        response.setEndTime(schedule.getEndTime());
        response.setRoom(schedule.getRoom());

        BaseResponse<CreateScheduleResponse> baseResponse = new BaseResponse<>(ApplicationCode.SUCCESS);
        baseResponse.setWsResponse(response);
        return baseResponse;
    }

    @Override
    @Transactional
    public BaseResponse<List<ScheduleInfoResponse>> getStudentSchedule(Long studentId) {

        List<Classes> classes = classMemberRepository.findClassesByStudentId(studentId);


        List<ScheduleInfoResponse> schedules = classes.stream()
                .flatMap(clazz -> clazz.getSchedules().stream()
                        .map(sch -> {
                            ScheduleInfoResponse info = new ScheduleInfoResponse();
                            info.setScheduleId(sch.getId());
                            info.setClassId(clazz.getId());
                            info.setClassName(clazz.getName());
                            info.setDate(sch.getDate());
                            info.setStartTime(sch.getStartTime());
                            info.setEndTime(sch.getEndTime());
                            info.setRoom(sch.getRoom());
                            return info;
                        })
                ).toList();

        BaseResponse<List<ScheduleInfoResponse>> baseResponse = new BaseResponse<>(ApplicationCode.SUCCESS);
        baseResponse.setWsResponse(schedules);
        return baseResponse;
    }

    @Override
    public BaseResponse<List<ScheduleInfoResponse>> getTeacherSchedule(Long teacherId) {
        List<Schedule> scheduleList = scheduleRepository.findByTeacherId(teacherId);

        List<ScheduleInfoResponse> schedules = scheduleList.stream().map(sch -> {
            ScheduleInfoResponse info = new ScheduleInfoResponse();
            info.setScheduleId(sch.getId());
            info.setClassId(sch.getClassEntity().getId());
            info.setClassName(sch.getClassEntity().getName());
            info.setDate(sch.getDate());
            info.setStartTime(sch.getStartTime());
            info.setEndTime(sch.getEndTime());
            info.setRoom(sch.getRoom());
            return info;
        }).toList();

        BaseResponse<List<ScheduleInfoResponse>> baseResponse = new BaseResponse<>(ApplicationCode.SUCCESS);
        baseResponse.setWsResponse(schedules);
        return baseResponse;
    }

    @Override
    @Transactional
    public BaseResponse<List<ScheduleInfoResponse>> getStudentScheduleByDate(Long studentId, ScheduleByDateRequest request) {
        List<Classes> classes = classMemberRepository.findClassesByStudentId(studentId);

        List<ScheduleInfoResponse> schedules = classes.stream()
                .flatMap(clazz -> clazz.getSchedules().stream()
                        .filter(s -> s.getDate().equals(request.getDate()))
                        .map(s -> {
                            ScheduleInfoResponse info = new ScheduleInfoResponse();
                            info.setScheduleId(s.getId());
                            info.setClassId(clazz.getId());
                            info.setClassName(clazz.getName());
                            info.setStartTime(s.getStartTime());
                            info.setEndTime(s.getEndTime());
                            info.setRoom(s.getRoom());
                            info.setTeacherName(s.getTeacher().getUsername());
                            return info;
                        })
                )
                .sorted((a,b) -> a.getStartTime().compareTo(b.getStartTime())) // sắp xếp theo thời gian bắt đầu
                .toList();

        BaseResponse<List<ScheduleInfoResponse>> baseResponse = new BaseResponse<>(ApplicationCode.SUCCESS);
        baseResponse.setWsResponse(schedules);
        return baseResponse;
    }

    @Override
    public BaseResponse<List<ScheduleInfoResponse>> getTeacherScheduleByDate(Long teacherId, ScheduleByDateRequest request) {
        List<Schedule> scheduleList = scheduleRepository.findByTeacherIdAndDate(teacherId, request.getDate());

        List<ScheduleInfoResponse> schedules = scheduleList.stream()
                .map(s -> {
                    ScheduleInfoResponse info = new ScheduleInfoResponse();
                    info.setScheduleId(s.getId());
                    info.setClassId(s.getClassEntity().getId());
                    info.setClassName(s.getClassEntity().getName());
                    info.setStartTime(s.getStartTime());
                    info.setEndTime(s.getEndTime());
                    info.setRoom(s.getRoom());
                    return info;
                })
                .sorted(Comparator.comparing(ScheduleInfoResponse::getStartTime))
                .toList();

        BaseResponse<List<ScheduleInfoResponse>> baseResponse = new BaseResponse<>(ApplicationCode.SUCCESS);
        baseResponse.setWsResponse(schedules);
        return baseResponse;
    }
    public BaseResponse<List<ScheduleInfoResponse>> getCurrentUserResponse(@RequestHeader("Authorization") String AuthHeader){
        if(AuthHeader == null || AuthHeader.isBlank()){
            throw new ApplicationException(ApplicationCode.INVALID_TOKEN);
        }
        String token = jwtUtil.extractToken(AuthHeader);
        Long id = jwtUtil.getIdFromToken(token);
        String role = jwtUtil.extractRole(token);
        switch (role) {
            case "teacher":
                return this.getTeacherSchedule(id);
            case "student":
                return this.getStudentSchedule(id);
            default:
                throw new ApplicationException(ApplicationCode.INVALID_ROLE);
        }

    }
}

package unicorns.backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import unicorns.backend.dto.request.AddStudentToClassRequest;
import unicorns.backend.dto.request.CreateClassRequest;
import unicorns.backend.dto.request.RemoveStudentsFromClassRequest;
import unicorns.backend.dto.response.*;
import unicorns.backend.entity.ClassMember;
import unicorns.backend.entity.Classes;
import unicorns.backend.entity.User;
import unicorns.backend.repository.ClassMemberRepository;
import unicorns.backend.repository.ClassRepository;
import unicorns.backend.repository.UserRepository;
import unicorns.backend.service.ClassService;
import unicorns.backend.util.ApplicationCode;
import unicorns.backend.util.ApplicationException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;
    private final UserRepository userRepository;
    private final ClassMemberRepository classMemberRepository;

    @Override
    public BaseResponse<List<GetClassResponse>> getAllClasses() {
        List<Classes> classes = classRepository.findAll();

        List<GetClassResponse> classResponses = classes.stream().map(classEntity -> {
            GetClassResponse response = new GetClassResponse();
            BeanUtils.copyProperties(classEntity, response);
            return response;
        }).collect(Collectors.toList());

        BaseResponse<List<GetClassResponse>> baseResponse = new BaseResponse<>(ApplicationCode.SUCCESS);
        baseResponse.setWsResponse(classResponses);
        return baseResponse;
    }

    @Override
    public BaseResponse<GetClassResponse> createClass(CreateClassRequest request) {

        Classes newClass = new Classes();
        newClass.setName(request.getName());
        newClass.setDescription(request.getDescription());

        Classes savedClass = classRepository.save(newClass);

        GetClassResponse response = new GetClassResponse();
        response.setId(savedClass.getId());
        response.setName(newClass.getName());
        response.setDescription(newClass.getDescription());

        BaseResponse<GetClassResponse> baseResponse = new BaseResponse<>(ApplicationCode.SUCCESS);
        baseResponse.setWsResponse(response);
        return baseResponse;
    }

    @Override
    public BaseResponse<DeleteClassResponse> deleteClass(Long id) {
        Optional<Classes> optionalClass = classRepository.findById(id);
        if (optionalClass.isEmpty()) {
            throw new ApplicationException(ApplicationCode.CLASS_NOT_FOUND);
        }

        Classes deletedClass = optionalClass.get();
        classRepository.delete(deletedClass);

        DeleteClassResponse response = new DeleteClassResponse();
        response.setId(deletedClass.getId());
        response.setName(deletedClass.getName());
        response.setDescription(deletedClass.getDescription());

        BaseResponse<DeleteClassResponse> baseResponse = new BaseResponse<>(ApplicationCode.SUCCESS);
        baseResponse.setWsResponse(response);
        return baseResponse;
    }

    @Override
    @Transactional
    public BaseResponse<AddStudentToClassResponse> addStudentsToClass(Long classId, AddStudentToClassRequest request) {
        Classes clazz = classRepository.findById(classId)
                .orElseThrow(() -> new ApplicationException(ApplicationCode.CLASS_NOT_FOUND));

        int addedCount = 0;
        for (Long studentId : request.getStudentIds()) {
            User student = userRepository.findById(studentId)
                    .orElseThrow(() -> new ApplicationException(ApplicationCode.USER_NOT_FOUND));


            boolean exists = classMemberRepository.existsByClassEntityIdAndStudentId(classId, studentId);
            if (!exists) {
                ClassMember cm = new ClassMember();
                cm.setClassEntity(clazz);
                cm.setStudent(student);
                classMemberRepository.save(cm);
                addedCount++;
            }
        }

        AddStudentToClassResponse response = new AddStudentToClassResponse();
        response.setClassId(clazz.getId());
        response.setClassName(clazz.getName());
        response.setAddedCount(addedCount);

        BaseResponse<AddStudentToClassResponse> baseResponse = new BaseResponse<>(ApplicationCode.SUCCESS);
        baseResponse.setWsResponse(response);
        return baseResponse;
    }

        @Override
        @Transactional
        public BaseResponse<RemoveStudentsFromClassResponse> removeStudentsFromClass(Long classId, RemoveStudentsFromClassRequest request) {
            Classes clazz = classRepository.findById(classId)
                    .orElseThrow(() -> new ApplicationException(ApplicationCode.CLASS_NOT_FOUND));

            int removedCount = 0;
            for (Long studentId : request.getStudentIds()) {
                ClassMember cm = (ClassMember) classMemberRepository.findByClassEntityIdAndStudentId(classId, studentId)
                        .orElse(null);
                if (cm != null) {
                    classMemberRepository.delete(cm);
                    removedCount++;
                }
            }

            RemoveStudentsFromClassResponse response = new RemoveStudentsFromClassResponse();
            response.setClassId(clazz.getId());
            response.setClassName(clazz.getName());
            response.setRemovedCount(removedCount);

            BaseResponse<RemoveStudentsFromClassResponse> baseResponse = new BaseResponse<>(ApplicationCode.SUCCESS);
            baseResponse.setWsResponse(response);
            return baseResponse;
    }
    @Override
    @Transactional
    public BaseResponse<GetClassByIdResponse> getClassById(Long id) {
        Classes clazz = classRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ApplicationCode.CLASS_NOT_FOUND));

        GetClassByIdResponse response = new GetClassByIdResponse();
        response.setId(clazz.getId());
        response.setName(clazz.getName());
        response.setDescription(clazz.getDescription());

        List<GetClassByIdResponse.StudentInfo> students = clazz.getMembers().stream()
                .map(cm -> {
                    GetClassByIdResponse.StudentInfo s = new GetClassByIdResponse.StudentInfo();
                    s.setId(cm.getStudent().getId());
                    s.setFullName(cm.getStudent().getName());
                    return s;
                }).toList();
        response.setStudents(students);

        BaseResponse<GetClassByIdResponse> baseResponse = new BaseResponse<>(ApplicationCode.SUCCESS);
        baseResponse.setWsResponse(response);
        return baseResponse;
    }
}


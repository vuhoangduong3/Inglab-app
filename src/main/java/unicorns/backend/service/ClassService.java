package unicorns.backend.service;

import org.springframework.web.bind.annotation.RequestHeader;
import unicorns.backend.dto.request.AddStudentToClassRequest;
import unicorns.backend.dto.request.CreateClassRequest;
import unicorns.backend.dto.request.RemoveStudentsFromClassRequest;
import unicorns.backend.dto.response.*;

import java.util.List;

public interface ClassService {
    BaseResponse<List<GetClassResponse>> getAllClasses(@RequestHeader("Authorization") String AuthHeader);
    BaseResponse<GetClassResponse> createClass(@RequestHeader("Authorization") String AuthHeader, CreateClassRequest request);
    BaseResponse<DeleteClassResponse> deleteClass(@RequestHeader("Authorization") String AuthHeader,Long id);
    BaseResponse<AddStudentToClassResponse> addStudentsToClass(@RequestHeader("Authorization") String AuthHeader,Long classId, AddStudentToClassRequest request);
    BaseResponse<RemoveStudentsFromClassResponse> removeStudentsFromClass(@RequestHeader("Authorization") String AuthHeader,Long classId, RemoveStudentsFromClassRequest request);
    BaseResponse<GetClassByIdResponse> getClassById(Long id);

}

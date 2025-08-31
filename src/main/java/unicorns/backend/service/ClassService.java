package unicorns.backend.service;

import unicorns.backend.dto.request.AddStudentToClassRequest;
import unicorns.backend.dto.request.CreateClassRequest;
import unicorns.backend.dto.request.RemoveStudentsFromClassRequest;
import unicorns.backend.dto.response.*;

import java.util.List;

public interface ClassService {
    BaseResponse<List<GetClassResponse>> getAllClasses();
    BaseResponse<GetClassResponse> createClass(CreateClassRequest request);
    BaseResponse<DeleteClassResponse> deleteClass(Long id);
    BaseResponse<AddStudentToClassResponse> addStudentsToClass(Long classId, AddStudentToClassRequest request);
    BaseResponse<RemoveStudentsFromClassResponse> removeStudentsFromClass(Long classId, RemoveStudentsFromClassRequest request);
    BaseResponse<GetClassByIdResponse> getClassById(Long id);

}

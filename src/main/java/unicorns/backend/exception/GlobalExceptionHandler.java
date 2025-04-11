package unicorns.backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import unicorns.backend.dto.response.BaseResponse;
import unicorns.backend.util.ApplicationCode;
import unicorns.backend.util.ApplicationException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
    @ExceptionHandler({ApplicationException.class})
    public ResponseEntity<BaseResponse> handleResponseException(ApplicationException e, HttpServletRequest request) {
        log.error("Failed to handle request " + request.getRequestURI() + ": " + e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<BaseResponse> handleResponseException(Exception e, HttpServletRequest request) {
        log.error("Failed to handle request " + request.getRequestURI() + ": " + e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse(ApplicationCode.UNKNOWN_ERROR, ApplicationCode.getMessage(ApplicationCode.UNKNOWN_ERROR)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
        );
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
        BaseResponse baseResponse = new BaseResponse(ApplicationCode.INPUT_INVALID);
        baseResponse.setMessage(message);
        return ResponseEntity.badRequest().body(baseResponse);
    }
}

package com.project.vaccine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    //MethodArgumentNotValidException --> lỗi do thư viện quăng ra

    // Xử lí lỗi validation (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        Map<String, String> fieldErrors = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        errorResponse.put("message", "VALIDATION_ERROR");
        errorResponse.put("details", fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    // Xử lí lỗi cú pháp JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleJsonParseError(HttpMessageNotReadableException ex) {
        Map<String, Object> msg = new HashMap<>();
        msg.put("message", "error");
        msg.put("details", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }

    // Xử lí lỗi duplicate
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateException(DuplicateException ex) {
        return ResponseEntity.badRequest().body(Map.of(
                "message", "Duplicate error",
                "errors", ex.getErrors()
        ));
    }

    // Xử lí lỗi not found
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "message", "Not found error",
                "errors", ex.getMessage()
        ));
    }

    // Xử lí lỗi authentication
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                "message", "Authentication error",
                "errors", ex.getMessage()
        ));
    }
}

package vn.edu.hcmuaf.fit.webbansach.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.webbansach.model.dto.BookDto;
import vn.edu.hcmuaf.fit.webbansach.model.entity.Books;
import vn.edu.hcmuaf.fit.webbansach.service.BookService;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> addBook(@Valid @RequestBody BookDto dto) {
        Books saved = bookService.createBook(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("id", saved.getId()));
    }

    // --- Exception Handlers ---
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage
                ));
        return ResponseEntity.badRequest().body(Map.of(
                "type", "VALIDATION_ERROR",
                "validationErrors", errors
        ));
    }

    @ExceptionHandler(DuplicateBookException.class)
    public ResponseEntity<Map<String, String>> handleDuplicate(DuplicateBookException ex) {
        return ResponseEntity.badRequest().body(Map.of(
                "type", ex.getCode(),
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleOther(Exception ex) {
        ex.printStackTrace(); // log để debug
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "type", "INTERNAL_ERROR",
                        "message", ex.getMessage() != null ? ex.getMessage() : "Có lỗi trong server"
                ));
    }

}

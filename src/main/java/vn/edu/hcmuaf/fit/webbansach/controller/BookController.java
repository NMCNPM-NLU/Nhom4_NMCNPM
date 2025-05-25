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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    // 13.1.1.5 BookController nhận dữ liệu JSON từ adminPage, ánh xạ vào DTO, và kiểm tra ràng buộc.
    @PostMapping
    public ResponseEntity<Map<String, Object>> addBook(@Valid @RequestBody BookDto dto) {
        // 13.1.1.6 BookController gọi BookService xử lý nghiệp vụ
        Books saved = bookService.createBook(dto);
        // 13.1.1.12 BookController trả về HTTP 201 cùng JSON { "id": savedId } khi thêm sách thành công
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("id", saved.getId()));
    }

    // 13.1.2.6 BookController trả về HTTP 400 cùng JSON type: "VALIDATION_ERROR", message: "Tương ứng được trả về trong Book"
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

    // 13.1.2.12 BookController trả về HTTP 400 cùng JSON type: "BOOK_DUPLICATE", message: "Sách với tiêu đề và tác giả này đã tồn tại"
    @ExceptionHandler(DuplicateBookException.class)
    public ResponseEntity<Map<String, String>> handleDuplicate(DuplicateBookException ex) {
        return ResponseEntity.badRequest().body(Map.of(
                "type", ex.getCode(),
                "message", ex.getMessage()
        ));
    }

    // 13.1.3.1 Nếu xảy ra lỗi bất ngờ thì BookController trả về HTTP 500 cùng JSON { type: "INTERNAL_ERROR", message: "Có lỗi trong server" }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleOther(Exception ex) {
        ex.printStackTrace(); // log để debug
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "type", "INTERNAL_ERROR",
                        "message", ex.getMessage() != null ? ex.getMessage() : "Có lỗi trong server"
                ));
    }

    // 13.4.4. BookController gọi phương thức getAllProducts() từ BookService.
    @GetMapping("/admin")
    public List<Books> apiBooks() {
        return bookService.getAllProducts();
    }
}


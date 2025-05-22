package vn.edu.hcmuaf.fit.webbansach.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.webbansach.model.dto.BookDto;
import vn.edu.hcmuaf.fit.webbansach.model.entity.Books;
import vn.edu.hcmuaf.fit.webbansach.service.BookService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<?> addBook(
            // 13.1.1.4 @RequestBody @Valid BookDto dto,
            //BindingResult bindingResult
            @RequestBody @Valid BookDto dto, BindingResult bindingResult // Thêm BindingResult để bắt lỗi
    ) {
        // Xử lý validation errors
        //13.1.2.6 if(bindingResult.hasErrors()){}
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
            //13.1.2.7 ResponseEntity.badRequest().body(errors)
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            // 2. Tạo sách
            //13.1.1.6 Gọi hàm createBook(dto)
            Books saved = bookService.createBook(dto);
            //13.1.1.11 ReponseEntity.status(HttpStatus.CREATE ).body(Map.of("id", saved.getId()))
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", saved.getId()));
        } catch (IllegalArgumentException ex) {
            // 3. Bắt trùng lặp
            //13.1.2.11 ResponseEntity.badRequest().body(Map.of("Error", ex.getMessage()))
            return ResponseEntity.badRequest().body(Map.of("Error", ex.getMessage()));
        }
    }

}

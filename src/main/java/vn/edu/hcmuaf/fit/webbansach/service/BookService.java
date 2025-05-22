package vn.edu.hcmuaf.fit.webbansach.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.webbansach.controller.DuplicateBookException;
import vn.edu.hcmuaf.fit.webbansach.model.dto.BookDto;
import vn.edu.hcmuaf.fit.webbansach.model.entity.Books;
import vn.edu.hcmuaf.fit.webbansach.model.entity.Categories;
import vn.edu.hcmuaf.fit.webbansach.repository.BookRepository;
import vn.edu.hcmuaf.fit.webbansach.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepo;
    private final CategoryRepository categoryRepo;

    public BookService(BookRepository bookRepo, CategoryRepository categoryRepo) {
        this.bookRepo = bookRepo;
        this.categoryRepo = categoryRepo;
    }

    @Transactional
    public Books createBook(BookDto dto) {
        if (bookRepo.existsByTitleAndAuthor(dto.getTitle(), dto.getAuthor())) {
            throw new DuplicateBookException("BOOK_DUPLICATE", "Sách với tiêu đề và tác giả này đã tồn tại");
        }

        Books book = new Books();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setDescription(dto.getDescription());
        book.setPrice(dto.getPrice());
        book.setStockQty(dto.getStockQty());
        book.setPublishedDate(dto.getPublishedDate());
        book.setImageUrl(
                Optional.ofNullable(dto.getImageUrl())
                        .filter(u -> !u.isBlank())
                        .orElse("/images/books/default.jpg")
        );

        // gán categories
        List<Categories> cats = categoryRepo.findAllById(dto.getCategoryIds());
        book.getCategories().addAll(cats);

        Books saved = bookRepo.save(book);
        return saved;
    }
}


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
        // 13.1.1.7 BookService kiểm tra sách đã tồn tại chưa.
        if (bookRepo.existsByTitleAndAuthor(dto.getTitle(), dto.getAuthor())) {
            // 13.1.2.11 BookService trả về lỗi sách đã tồn tại
            throw new DuplicateBookException("BOOK_DUPLICATE", "Sách với tiêu đề và tác giả này đã tồn tại");
        }
        // 13.1.1.8 Database bookstore trả về kết quả sách chưa tồn tại.
        // 3.1.1.9 BookService khởi tạo sách, lưu danh mục và lưu sách.
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

        // 13.1.1.10 Database bookstore trả về sách mới và ID.
        // 13.1.1.11 BookService lưu sách vào transactions, và trả về đôi tượng đã lưu bao gồm Id mới sinh
        return saved;
    }


    public List<Books> getAllProducts() {
        return bookRepo.findAll();
    }

}


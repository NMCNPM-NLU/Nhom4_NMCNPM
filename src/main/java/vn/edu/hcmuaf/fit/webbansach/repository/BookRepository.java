package vn.edu.hcmuaf.fit.webbansach.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.hcmuaf.fit.webbansach.model.entity.Books;

import java.util.List;

public interface BookRepository extends JpaRepository<Books, Integer> {
    boolean existsByTitleAndAuthor(String title, String author);
    // 6.1.4: Hệ thống truy vấn cơ sở dữ liệu
    // Truy vấn sách có title hoặc author chứa từ khóa (không phân biệt hoa thường)
    @Query("SELECT b FROM Books b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(b.author) LIKE LOWER(CONCAT('%', :query, '%'))")
    // 6.1.5: Cơ sở dữ liệu trả về danh sách sách khớp với từ khóa
    List<Books> searchBooks(@Param("query") String query);
}
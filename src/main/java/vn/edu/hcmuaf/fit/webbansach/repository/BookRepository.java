package vn.edu.hcmuaf.fit.webbansach.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.hcmuaf.fit.webbansach.model.entity.Books;

import java.util.List;

public interface BookRepository extends JpaRepository<Books, Integer> {
    boolean existsByTitleAndAuthor(String title, String author);

    @Query("SELECT b FROM Books b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(b.author) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Books> searchBooks(@Param("query") String query);
}
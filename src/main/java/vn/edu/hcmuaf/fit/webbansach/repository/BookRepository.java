package vn.edu.hcmuaf.fit.webbansach.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hcmuaf.fit.webbansach.model.entity.Books;

public interface BookRepository extends JpaRepository<Books, Long> {
    boolean existsByTitleAndAuthor(String title, String author);
}

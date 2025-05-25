package vn.edu.hcmuaf.fit.webbansach.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hcmuaf.fit.webbansach.model.entity.Categories;

public interface CategoryRepository extends JpaRepository<Categories, Integer> {
}
package vn.edu.hcmuaf.fit.webbansach.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.hcmuaf.fit.webbansach.model.dto.BookDto;
import vn.edu.hcmuaf.fit.webbansach.service.BookService;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private BookService bookService;
    // 6.1.1 và 6.1.2: Khách hàng bắt đầu tìm kiếm và nhập từ khóa
    // Yêu cầu GET đến endpoint "/search" được gửi từ form tìm kiếm trong index.jsp hoặc search.jsp
    @GetMapping("/search")
    public String searchBooks(@RequestParam("query") String query, Model model) {
        try {
            // 6.1.3: Hệ thống xử lý yêu cầu tìm kiếm
            // Kiểm tra tính hợp lệ của từ khóa (query): nếu rỗng hoặc null, đặt thông báo lỗi
            if (query == null || query.trim().isEmpty()) {
                model.addAttribute("error", "Search query cannot be empty.");
                model.addAttribute("searchResults", List.of());
                model.addAttribute("query", "");
                // 6.1.6: Nếu từ khóa không hợp lệ, hiển thị thông báo lỗi trên search.jsp
                return "/search";
            }
            // 6.1.4: Nếu từ khóa hợp lệ, hệ thống truy vấn cơ sở dữ liệu qua BookService
            List<BookDto> searchResults = bookService.searchBooks(query.trim());
            // 6.1.5: Cơ sở dữ liệu trả về kết quả (được xử lý qua BookService và BookRepository)
            // 6.1.6: Nếu từ khóa hợp lệ, hiển thị danh sách sách hoặc thông báo "Không tìm thấy"
            model.addAttribute("searchResults", searchResults);
            model.addAttribute("query", query.trim());
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("searchResults", List.of());
            model.addAttribute("query", query.trim());
        }
        // 6.1.7: Khách hàng nhận kết quả trên trang search.jsp
        return "/search"; // Maps to search.jsp
    }
}
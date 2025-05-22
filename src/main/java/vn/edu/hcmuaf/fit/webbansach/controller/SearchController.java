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

    @GetMapping("/search")
    public String searchBooks(@RequestParam("query") String query, Model model) {
        try {
            if (query == null || query.trim().isEmpty()) {
                model.addAttribute("error", "Search query cannot be empty.");
                model.addAttribute("searchResults", List.of());
                model.addAttribute("query", "");
                return "/search";
            }

            List<BookDto> searchResults = bookService.searchBooks(query.trim());
            model.addAttribute("searchResults", searchResults);
            model.addAttribute("query", query.trim());
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("searchResults", List.of());
            model.addAttribute("query", query.trim());
        }
        return "/search"; // Maps to search.jsp
    }
}
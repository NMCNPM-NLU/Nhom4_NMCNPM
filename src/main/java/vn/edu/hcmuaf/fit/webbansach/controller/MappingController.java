package vn.edu.hcmuaf.fit.webbansach.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MappingController {

    // Khi vào root (/), chuyển hướng sang /homePage
    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/homePage";
    }

    // Khi vào /homePage, trả về trang index.jsp
    @GetMapping("/homePage")
    public String homePage() {
        return "/index";
    }

    @GetMapping("/adminPage")
    public String admin() {
        return "/admin";
    }
}
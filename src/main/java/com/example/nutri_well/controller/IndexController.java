package com.example.nutri_well.controller;

import com.example.nutri_well.config.auth.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        //src/main/resources/templates/indecs.mustache
        return "indecs";
    }

    @GetMapping("/index.do")
    public String indexHtml(Model model) {
        //src/main/resources/static/index.html
        return "index";
    }
}

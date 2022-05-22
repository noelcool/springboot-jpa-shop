package com.shop.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Tag(name = "login", description = "login API")
@RequestMapping("/login")
public class LoginController {

    @GetMapping(value = "")
    public String loginMember() {
        return "member/memberLoginForm";
    }

    @GetMapping(value = "/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "member/memberLoginForm";
    }

}

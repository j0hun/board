package com.jyhun.board.domain.member.controller;

import com.jyhun.board.domain.member.dto.RegisterDTO;
import com.jyhun.board.domain.member.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LoginPageController {

    private final LoginService loginService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "member/register";
    }

    @GetMapping("/login")
    public String loginForm(){
        return "member/login";
    }

}
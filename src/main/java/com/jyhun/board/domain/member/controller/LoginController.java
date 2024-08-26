package com.jyhun.board.domain.member.controller;

import com.jyhun.board.domain.member.dto.RegisterDTO;
import com.jyhun.board.domain.member.service.LoginService;
import com.jyhun.board.global.exception.CustomException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "member/registerForm";
    }

    @PostMapping("/register")
    public String register(@Valid RegisterDTO registerDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "member/registerForm";
        }

        try {
            loginService.register(registerDTO);
        } catch (CustomException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/registerForm";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginMember(){
        return "member/loginForm";
    }

    @GetMapping("/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 확인해주세요.");
        return "member/loginForm";
    }


}
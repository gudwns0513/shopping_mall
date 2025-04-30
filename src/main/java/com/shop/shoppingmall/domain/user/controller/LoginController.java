package com.shop.shoppingmall.domain.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // 로그인 페이지를 반환
    @GetMapping("/login")
    public String login() {
        return "login";  // login.html 템플릿을 반환
    }

    // 로그아웃 페이지를 반환
    @GetMapping("/logout")
    public String logout() {
        return "logout";  // logout.html 템플릿을 반환
    }
}

package com.shop.shoppingmall.domain.user.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // 세션 무효화 (로그아웃 처리)
        return ResponseEntity.ok("로그아웃되었습니다.");
    }

    @GetMapping("/me")
    public ResponseEntity<String> getCurrentUser(HttpSession session) {
        Object userIdObj = session.getAttribute("USER_ID");

        if (userIdObj == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        String userId = userIdObj.toString(); // 세션에 저장할 때 String으로 저장했으니까
        return ResponseEntity.ok("현재 로그인한 유저 ID: " + userId);
    }
}

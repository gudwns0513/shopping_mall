package com.shop.shoppingmall.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.shoppingmall.global.response.CommonResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    //비 로그인 이용자가 서비스 접근 시 예외 처리
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException{

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        response.setContentType("application/json;charset=UTF-8");

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .data(null)
                .message("로그인 후 이용해주세요.")
                .build();

        response.getWriter().write(new ObjectMapper().writeValueAsString(commonResponse));
    }

}

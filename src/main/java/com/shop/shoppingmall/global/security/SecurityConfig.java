package com.shop.shoppingmall.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.shoppingmall.domain.user.oauth.OAuth2UserService;
import com.shop.shoppingmall.global.response.CommonResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;

    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
                .exceptionHandling(e -> e.authenticationEntryPoint(authenticationEntryPoint))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").authenticated() // 인증이 필요한 API
                        .anyRequest().permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/api/logout") // 로그아웃 요청 URL
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.setContentType("application/json;charset=UTF-8");

                            // CommonResponse 사용하여 응답 생성
                            CommonResponse<String> responseBody = CommonResponse.<String>builder()
                                    .code(HttpStatus.OK.value())
                                    .data(null)
                                    .message("로그아웃이 정상적으로 처리되었습니다.")
                                    .build();

                            // 응답을 JSON으로 작성
                            response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
                        })
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 쿠키 제거
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(successHandler()) // 로그인 성공 처리
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService) // 사용자 정보 매핑 서비스
                        )
                );

        return http.build();
    }


    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return ((request, response, authentication) -> {
            DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

            //사용자 정보 가져오기
            String userId = defaultOAuth2User.getAttributes().get("id").toString();
            String kakaoId = defaultOAuth2User.getAttributes().get("kakaoId").toString();
            String nickname = defaultOAuth2User.getAttributes().get("nickname").toString();

            //로그인 성공 시 내려줄 JSON
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());

            String body = """
                            {
                                "id":"%s",
                                "kakaoId":"%s",
                                "nickname":"%s"
                            }
                          """.formatted(userId, kakaoId, nickname);

            PrintWriter writer = response.getWriter();
            writer.println(body);
            writer.flush();
        });
    }




}

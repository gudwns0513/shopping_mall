package com.shop.shoppingmall.global.security;

import com.shop.shoppingmall.domain.user.oauth.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

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

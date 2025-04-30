package com.shop.shoppingmall.domain.user.oauth;

import com.shop.shoppingmall.domain.user.domain.User;
import com.shop.shoppingmall.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        if (!"kakao".equals(registrationId)) {
            throw new OAuth2AuthenticationException("카카오 로그인으로 진행하세요");
        }

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        Long kakaoId = (Long) attributes.get("id");
        String nickname = (String) properties.get("nickname");

        // 사용자 저장 또는 조회
        User user = userRepository.findByKakaoId(kakaoId)
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .kakaoId(kakaoId)
                                .nickname(nickname)
                                .build()
                ));

        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority("ROLE_USER")),
                Map.of("id", user.getId(), "kakaoId", user.getKakaoId(), "nickname", user.getNickname()), // 나중에 successHandler에서 이걸 활용
                "id"
        );
    }
}



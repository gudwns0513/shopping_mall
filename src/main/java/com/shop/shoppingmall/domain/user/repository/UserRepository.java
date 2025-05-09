package com.shop.shoppingmall.domain.user.repository;

import com.shop.shoppingmall.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByKakaoId(Long kakaoId);

    Optional<User> findByJsessionId(String jsessionId);
}

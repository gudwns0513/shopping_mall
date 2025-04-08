package com.shop.shoppingmall;

import com.shop.shoppingmall.domain.User;
import com.shop.shoppingmall.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
class ShoppingmallApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	void testEnvironment() {
		User user = User.builder()
						.username("test")
						.build();
		userRepository.save(user);
		userRepository.findById(1L);
	}

	@Test
	void devEnvironment() {
		User user = User.builder()
				.username("dev")
				.build();
		userRepository.save(user);
		userRepository.findById(2L);
	}

}

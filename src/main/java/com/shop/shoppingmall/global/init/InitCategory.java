package com.shop.shoppingmall.global.init;


import com.shop.shoppingmall.domain.category.domain.Category;
import com.shop.shoppingmall.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitCategory implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        if(categoryRepository.count() == 0) {
            List<String> categoryNames = List.of("가전제품", "식품", "패션", "예술", "생활용품", "차량", "취미");

            categoryNames
                    .forEach(name -> categoryRepository.save(
                                    Category.builder()
                                            .name(name)
                                            .build()
                            )
                    );
        }
    }
}

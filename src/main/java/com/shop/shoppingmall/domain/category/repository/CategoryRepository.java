package com.shop.shoppingmall.domain.category.repository;

import com.shop.shoppingmall.domain.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}

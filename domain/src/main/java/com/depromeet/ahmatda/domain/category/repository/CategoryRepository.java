package com.depromeet.ahmatda.domain.category.repository;

import com.depromeet.ahmatda.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

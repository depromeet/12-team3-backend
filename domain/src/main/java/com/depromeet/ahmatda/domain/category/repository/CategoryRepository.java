package com.depromeet.ahmatda.domain.category.repository;

import com.depromeet.ahmatda.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT category " +
            "FROM Category category " +
            "JOIN category.user user " +
            "WHERE user.userToken=:userToken")
    List<Category> findAllByUserToken(String userToken);
}

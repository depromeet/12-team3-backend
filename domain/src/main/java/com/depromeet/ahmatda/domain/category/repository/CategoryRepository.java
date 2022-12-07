package com.depromeet.ahmatda.domain.category.repository;

import com.depromeet.ahmatda.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT category FROM Category category WHERE category.user.deviceId=:deviceId")
    List<Category> findAllByDeviceId(String deviceId);
}

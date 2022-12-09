package com.depromeet.ahmatda.domain.item.repository;

import com.depromeet.ahmatda.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}

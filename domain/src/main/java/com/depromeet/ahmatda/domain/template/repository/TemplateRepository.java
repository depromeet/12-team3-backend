package com.depromeet.ahmatda.domain.template.repository;

import com.depromeet.ahmatda.domain.template.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
    @Query(value = "select distinct t from Template t" +
            " join fetch t.user" +
            " left join fetch t.items" +
            " where t.category.id = :categoryId and t.user.userToken = :userId order by t.isPin desc, t.createdAt")
    List<Template> findByCategoryAndUserId(Long categoryId, String userId);

    @Modifying
    @Query("update Template t set t.isPin = false where t.category.id = :categoryId")
    int templatesAllOffPin(Long categoryId);

    @Query("select count(i) > 0 from Item i" +
            " join i.template t " +
            " join t.user u " +
            " where u.userToken = :userToken and t.id = :templateId and i.categoryId =:categoryId " +
            " and REPLACE(i.name, ' ', '') = REPLACE(:itemName, ' ', '')")
    Boolean checkDuplicateItem(String userToken, Long templateId, Long categoryId, String itemName);

}

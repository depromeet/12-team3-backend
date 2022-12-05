package com.depromeet.ahmatda.domain.template;

import javax.persistence.*;

import com.depromeet.ahmatda.domain.BaseTimeEntity;
import com.depromeet.ahmatda.domain.Item;
import com.depromeet.ahmatda.domain.category.Category;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Template extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO : User Entity 연관관계 매핑
    private Long userId;

    private String templateName;

    @OneToMany(mappedBy = "template")
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

}

package com.depromeet.ahmatda.domain.recommend;

import com.depromeet.ahmatda.domain.BaseTimeEntity;
import com.depromeet.ahmatda.domain.category.CategoryType;
import com.depromeet.ahmatda.domain.emoji.AhmatdaEmoji;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class RecommendCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private CategoryType type;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private AhmatdaEmoji emoji;
}

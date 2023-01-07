package com.depromeet.ahmatda.domain.recommend;

import com.depromeet.ahmatda.domain.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class RecommendCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recomment_category_id")
    private RecommendCategory recommendCategory;

    @Column
    private String sectionName;
}

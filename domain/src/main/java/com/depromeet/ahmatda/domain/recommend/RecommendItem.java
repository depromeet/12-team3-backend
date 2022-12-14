package com.depromeet.ahmatda.domain.recommend;

import com.depromeet.ahmatda.domain.BaseTimeEntity;
import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.template.Template;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
public class RecommendItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recommend_template_id")
    private RecommendTemplate recommendTemplate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recommend_section_id")
    private RecommendSection recommendSection;

    @Column
    private String itemName;
}

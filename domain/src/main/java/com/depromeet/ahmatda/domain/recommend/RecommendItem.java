package com.depromeet.ahmatda.domain.recommend;

import com.depromeet.ahmatda.domain.BaseTimeEntity;
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

    @Column
    private Long categoryId;

    @Column
    private String itemName;

}

package com.depromeet.ahmatda.domain.recommend;

import javax.persistence.*;

import com.depromeet.ahmatda.domain.BaseTimeEntity;
import com.depromeet.ahmatda.domain.category.Category;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class RecommendTemplate extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String templateName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommend_category_id")
    private RecommendCategory recommendCategory;

    @OneToMany(mappedBy = "recommendTemplate", cascade = CascadeType.REMOVE)
    private List<RecommendItem> recommendItems = new ArrayList<>();
}

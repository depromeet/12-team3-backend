package com.depromeet.ahmatda.domain.recommend;

import javax.persistence.*;

import com.depromeet.ahmatda.domain.category.Category;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class RecommendTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String templateName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "recommendTemplate")
    private List<RecommendItem> recommendItems = new ArrayList<>();

}

package com.depromeet.ahmatda.domain.category;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.depromeet.ahmatda.domain.BaseTimeEntity;
import com.depromeet.ahmatda.domain.RecommendTemplate;
import com.depromeet.ahmatda.domain.Template;
import com.depromeet.ahmatda.domain.user.User;
import lombok.Getter;

@Getter
@Entity
public class Category extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "category")
    private List<Template> templates = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private List<RecommendTemplate> recommendTemplates = new ArrayList<>();

    @Column
    private String type;

    @Column
    private String name;

    @Column
    private String emoji;
}

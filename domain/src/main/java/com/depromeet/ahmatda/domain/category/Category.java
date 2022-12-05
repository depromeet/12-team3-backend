package com.depromeet.ahmatda.domain.category;

import com.depromeet.ahmatda.domain.BaseTimeEntity;
import com.depromeet.ahmatda.domain.RecommendTemplate;
import com.depromeet.ahmatda.domain.template.Template;
import com.depromeet.ahmatda.domain.user.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    private Emoji emoji;
}

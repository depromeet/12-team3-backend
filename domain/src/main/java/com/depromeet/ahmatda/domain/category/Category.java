package com.depromeet.ahmatda.domain.category;

import com.depromeet.ahmatda.domain.BaseTimeEntity;
import com.depromeet.ahmatda.domain.recommend.RecommendTemplate;
import com.depromeet.ahmatda.domain.emoji.AhmatdaEmoji;
import com.depromeet.ahmatda.domain.template.Template;
import com.depromeet.ahmatda.domain.user.User;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<Template> templates = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<RecommendTemplate> recommendTemplates = new ArrayList<>();

    @Column
    @Enumerated(EnumType.STRING)
    private CategoryType type;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private AhmatdaEmoji emoji;

    public boolean authenticateUser(final String userToken) {
        return this.user.getUserToken().equals(userToken);
    }
}

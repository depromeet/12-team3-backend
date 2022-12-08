package com.depromeet.ahmatda.domain.template;

import javax.persistence.*;

import com.depromeet.ahmatda.domain.BaseTimeEntity;
import com.depromeet.ahmatda.domain.Item;
import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Template extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User user;

    private String templateName;

    @OneToMany(mappedBy = "template")
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    //==연관관계 메서드==//
    public void addUserTemplateItem(Item item){
        items.add(item);
    }

    //==생성 메서드==//
    public static Template createTemplate(String templateName, Category category, User user) {
        Template template = new Template();
        template.setTemplateName(templateName);
        template.setUser(user);
        template.setCategory(category);
        return template;
    }
}

package com.depromeet.ahmatda.domain.template;

import javax.persistence.*;

import com.depromeet.ahmatda.domain.BaseTimeEntity;
import com.depromeet.ahmatda.domain.item.Item;
import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Template extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String templateName;

    @OneToMany(mappedBy = "template")
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private boolean isReservedAlarm;

    private boolean isPin;

    //==연관관계 메서드==//
    public void addUserTemplateItem(Item item){
        items.add(item);
    }

    //==생성 메서드==//
    public static Template createTemplate(String templateName, Category category, User user) {
        Template template = new Template();
        template.templateName = templateName;
        template.category = category;
        template.user = user;
        template.isReservedAlarm = false;
        template.isPin = false;
        return template;
    }

    public static Template modifyTemplateName(Template template, String templateName) {
        template.templateName = templateName;
        return template;
    }

    public static Template modifyTemplateIsPIn(Template template, boolean isPin) {
        template.isPin = isPin;
        return template;
    }

    //== 비지니스 로직 ==//
    public boolean authenticateUser(String userId) {
        return this.user.getUserToken().equals(userId);
    }
}

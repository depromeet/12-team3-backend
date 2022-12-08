package com.depromeet.ahmatda.domain;

import com.depromeet.ahmatda.domain.template.Template;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long categoryId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "template_id")
    private Template template;

    @Column
    private String name;

    @Column
    private Long alarmId;

    @Column
    private Long isTake;

    //==생성 메서드==//
    public static Item createItem(Long categoryId, Template template, String name) {
        Item item = new Item();
        item.setName(name);
        item.setTemplate(template);
        item.setCategoryId(categoryId);
        return item;
    }
}

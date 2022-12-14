package com.depromeet.ahmatda.domain.item;

import com.depromeet.ahmatda.domain.BaseTimeEntity;
import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.template.Template;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
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
    private boolean isTake;

    @Column
    private boolean isImportant;

    //==생성 메서드==//
    public static Item createItem(Long categoryId, Template template, String name) {
        Item item = new Item();
        item.categoryId = categoryId;
        item.template = template;
        item.name = name;
        item.isTake = false;
        item.isImportant = false;
        return item;
    }

    public static Item UserTemplateAddItem(Long categoryId, Template template, String itemName, boolean important) {
        Item item = new Item();
        item.categoryId = categoryId;
        item.template = template;
        item.name = itemName;
        item.isTake = false;
        item.isImportant = important;
        return item;
    }

    public static Item modfiyItemNameAndIsImportant(Item item, String itemName, boolean isImportant) {
        item.isImportant = isImportant;
        item.name = itemName;
        return item;
    }

    public static Item modifyItemIsTack(Item item, boolean isTake) {
        item.isTake = isTake;
        return item;
    }
}

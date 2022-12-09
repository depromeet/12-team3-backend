package com.depromeet.ahmatda.domain.item;

import com.depromeet.ahmatda.domain.BaseTimeEntity;
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
    private Long alarmId;

    @Column
    private boolean isTake;

    //==생성 메서드==//
    public static Item createItem(Long categoryId, Template template, String name) {
        Item item = new Item();
        item.categoryId = categoryId;
        item.template = template;
        item.name = name;
        //TODO : 알람 ID는 임의로 저장 추 후 변경
        item.alarmId = 0L;
        item.isTake = false;
        return item;
    }
}

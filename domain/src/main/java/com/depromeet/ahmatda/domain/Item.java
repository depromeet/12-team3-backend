package com.depromeet.ahmatda.domain;

import com.depromeet.ahmatda.domain.template.Template;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
public class Item extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long categoryId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "itemlist_id")
    private ItemList itemList;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "template_id")
    private Template template;

    @Column
    private String name;

    @Column
    private String emoji;

    @Column
    private Long alarmId;

    @Column
    private Long isTake;
}

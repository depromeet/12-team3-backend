package com.depromeet.ahmatda.domain;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
public class Item extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long templateId;

    @Column
    private Long categoryId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "itemlist_id")
    private ItemList itemList;

    @Column
    private String name;

    @Column
    private String emoji;

    @Column
    private Long alarmId;

    @Column
    private Long isTake;
}

package com.depromeet.ahmatda.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Item extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long templateId;

    @Column
    private Long categoryId;

    @Column
    private String name;

    @Column
    private String emoji;

    @Column
    private Long alarmId;

    @Column
    private Long isTake;
}

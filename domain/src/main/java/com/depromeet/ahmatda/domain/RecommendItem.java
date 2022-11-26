package com.depromeet.ahmatda.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class RecommendItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long templateId;

    @Column
    private Long categoryId;

    @Column
    private String itemName;

    @Column
    private String itemEmoji;
}

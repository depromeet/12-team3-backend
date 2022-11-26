package com.depromeet.ahmatda.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class UserSuggestion extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private String typeSuggestion;

    @Column
    private String itemSuggestion;
}

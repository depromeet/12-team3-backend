package com.depromeet.ahmatda.domain;

import com.depromeet.ahmatda.domain.template.Template;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class ItemList extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String listName;

    //TODO: 리스트 알람 추가 필요

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private Template template;

    @OneToMany(mappedBy = "itemList")
    private List<Item> items = new ArrayList<>();


}

package com.depromeet.ahmatda.advise;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public class TestJacoco {

    private String name;
    private String title;

    public int subs() {
        return 1+1;
    }
}

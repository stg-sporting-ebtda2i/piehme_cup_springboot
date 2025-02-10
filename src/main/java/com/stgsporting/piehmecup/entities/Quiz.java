package com.stgsporting.piehmecup.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Quiz {

    private Long id;
    private String name;
    private String slug;
    private SchoolYear schoolYear;

}

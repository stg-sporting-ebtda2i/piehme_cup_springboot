package com.stgsporting.piehmecup.entities;

import lombok.Getter;
import lombok.Setter;
import net.minidev.json.JSONObject;

@Setter
@Getter
public class Option {
    private Long id;
    private String name;
    private long order;
    private String picture;

    public static Option fromJson(JSONObject optionJson) {
        Option option = new Option();
        option.setId((Long) optionJson.get("id"));
        option.setName((String) optionJson.get("name"));
        option.setOrder((long) optionJson.get("order"));
        option.setPicture((String) optionJson.get("picture"));
        return option;
    }
}

package com.stgsporting.piehmecup.entities;

import com.stgsporting.piehmecup.enums.QuestionType;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Question {

    private Long id;
    private String title;
    private String picture;
    private QuestionType type;
    private Long coins;
    private List<Option> options;

    public Question() {
        options = new ArrayList<>();
    }

    public static Question fromJson(JSONObject questionJson) {
        Question question = new Question();
        question.setId((Long) questionJson.get("id"));
        question.setTitle((String) questionJson.get("title"));
        question.setPicture((String) questionJson.get("picture"));
        question.setCoins((Long) questionJson.get("points"));
        question.setType(QuestionType.fromId(Math.toIntExact((Long) questionJson.get("type"))));

        JSONArray optionsArray = (JSONArray) questionJson.get("options");
        if (optionsArray != null) {
            for (Object optionObject : optionsArray) {
                JSONObject optionJson = (JSONObject) optionObject;
                Option option = Option.fromJson(optionJson);
                question.addOption(option);
            }
        }

        return question;
    }

    public void addOption(Option option) {
        if (options == null) {
            options = new ArrayList<>();
        }

        options.add(option);
    }
}

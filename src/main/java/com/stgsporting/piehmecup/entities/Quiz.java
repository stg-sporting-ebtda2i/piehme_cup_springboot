package com.stgsporting.piehmecup.entities;

import lombok.Getter;
import lombok.Setter;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Quiz {

    private Long id;
    private String name;
    private String slug;
    private Long coins;
    private String publishedAt;
    private Boolean isSolved;
    private Long coinsEarned;
//    private SchoolYear schoolYear;
    private List<Question> questions;
    private Long questionsCount;

    public static Quiz fromJson(JSONObject quizJson) {
        Quiz quiz = new Quiz();
        quiz.setId((Long) quizJson.get("id"));
        quiz.setName((String) quizJson.get("name"));
        quiz.setSlug((String) quizJson.get("slug"));
        quiz.setCoins((Long) quizJson.get("points"));
        quiz.setCoinsEarned((Long) quizJson.getOrDefault("points_won", 0L));
        quiz.setIsSolved((Boolean) quizJson.getOrDefault("is_solved", false));
//        quiz.setPublishedAt(Date.from(Instant.parse(quizJson.getAsString("published_at"))));
        quiz.setPublishedAt(quizJson.getAsString("published_at"));

        quiz.setQuestionsCount((Long) quizJson.get("questions_count"));


        JSONArray questionsArray = (JSONArray) quizJson.get("questions");
        if (questionsArray != null) {
            for (Object questionObject : questionsArray) {
                JSONObject questionJson = (JSONObject) questionObject;
                Question question = Question.fromJson(questionJson);
                quiz.addQuestion(question);
            }
        }

        return quiz;
    }

    public void addQuestion(Question question) {
        if (questions == null) {
            questions = new ArrayList<>();
        }

        questions.add(question);
    }
}

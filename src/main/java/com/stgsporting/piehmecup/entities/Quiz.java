package com.stgsporting.piehmecup.entities;

import com.stgsporting.piehmecup.dtos.users.UserResponseDTO;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
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
    private String bonusBefore;
    private Long bonus;

    private Boolean isSolved;
    private Long coinsEarned;
//    private SchoolYear schoolYear;
    private List<Question> questions;
    private List<UserResponseDTO> responses;
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

        if(quizJson.get("data") != null) {
            JSONObject data = (JSONObject) quizJson.get("data");
            if (data.containsKey("bonusBefore") && data.containsKey("bonus")) {

                quiz.setBonusBefore(data.getAsString("bonusBefore"));
                quiz.setBonus((Long) data.get("bonus"));
            }
        }

        if(quizJson.containsKey("responses")) {
            JSONArray responsesArray = (JSONArray) quizJson.get("responses");
            if (responsesArray != null) {
                for (Object responseObject : responsesArray) {
                    JSONObject responseJson = (JSONObject) responseObject;
                    UserResponseDTO response = UserResponseDTO.fromJSON(responseJson);
                    quiz.addResponse(response);
                }
            }
        }

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

    public Date bonusBeforeDate() {
        if (this.bonusBefore == null) return null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(this.bonusBefore, formatter);

        return Date.from(dateTime.toInstant(ZoneOffset.UTC));
    }

    public static Date dateFromString(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        return Date.from(dateTime.toInstant(ZoneOffset.UTC));
    }

    public Boolean shouldAddBonus() {
        return shouldAddBonus(Date.from(Instant.now()));
    }

    public Boolean shouldAddBonus(String time) {
        return shouldAddBonus(dateFromString(time));
    }

    public Boolean shouldAddBonus(Date time) {
        return this.getBonus() != null
                && this.bonusBefore != null
                && this.bonusBeforeDate().getTime() > time.getTime();
    }

    public void addResponse(UserResponseDTO responseDTO) {
        if (responses == null) {
            responses = new ArrayList<>();
        }

        responses.add(responseDTO);
    }

    public void addQuestion(Question question) {
        if (questions == null) {
            questions = new ArrayList<>();
        }

        questions.add(question);
    }
}

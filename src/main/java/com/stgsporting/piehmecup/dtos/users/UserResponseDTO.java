package com.stgsporting.piehmecup.dtos.users;

import lombok.Getter;
import lombok.Setter;
import net.minidev.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class UserResponseDTO {

    private Long id;
    private String username;
    private Integer coins;
    private Map<String, Answer> answers;
    private Integer correctQuestionsCount;
    private String submittedAt;

    @Getter
    @Setter
    public static class Answer {
        private Long id;
//        private Long questionId;
        private Object answer;
        private Boolean isCorrect;
        private Integer coins;
    }

    public static UserResponseDTO fromJSON(JSONObject responseJSON) {
        UserResponseDTO response =  new UserResponseDTO();

        response.setUsername(((JSONObject) responseJSON.get("entity")).getAsString("name"));
        response.setId(responseJSON.getAsNumber("id").longValue());
        response.setCoins(responseJSON.getAsNumber("points").intValue());
        response.setSubmittedAt(responseJSON.getAsString("created_at"));

        JSONObject answersJSON = (JSONObject) responseJSON.get("answers");
        response.answers = new HashMap<>();
        for (String key : answersJSON.keySet()) {
            JSONObject answerJSON = (JSONObject) answersJSON.get(key);

            Answer answer = new Answer();
            answer.setId(answerJSON.getAsNumber("id").longValue());
            answer.setCoins(answerJSON.getAsNumber("points").intValue());
            answer.setIsCorrect(answerJSON.getAsNumber("is_correct").intValue() == 1);
            answer.setAnswer(answerJSON.get("answer"));
            response.answers.put(key, answer);
        }

        response.setCorrectQuestionsCount(response.answers.values().stream().mapToInt(answer -> answer.isCorrect ? 1 : 0).sum());

        return response;
    }
}

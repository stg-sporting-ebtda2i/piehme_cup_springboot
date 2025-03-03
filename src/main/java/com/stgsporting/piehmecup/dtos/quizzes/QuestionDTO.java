package com.stgsporting.piehmecup.dtos.quizzes;

import com.stgsporting.piehmecup.entities.Option;
import com.stgsporting.piehmecup.entities.Question;
import com.stgsporting.piehmecup.entities.Quiz;
import com.stgsporting.piehmecup.enums.QuestionType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class QuestionDTO {
    private Long id;
    private String title;
    private String picture;
    private QuestionType type;
    private Long coins;
    private List<Option> options;

    public QuestionDTO(Question question) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.picture = question.getPicture();
        this.coins = question.getCoins();
        this.type = question.getType();
        this.options = question.getOptions();
    }
}

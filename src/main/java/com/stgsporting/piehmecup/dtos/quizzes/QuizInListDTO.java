package com.stgsporting.piehmecup.dtos.quizzes;

import com.stgsporting.piehmecup.entities.Quiz;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class QuizInListDTO {
    private Long id;
    private String name;
    private String slug;
    private Integer coins;
    private Boolean isSolved;

    public QuizInListDTO(Quiz quiz) {
        this.id = quiz.getId();
        this.name = quiz.getName();
        this.slug = quiz.getSlug();
        this.coins = quiz.getCoins();
        this.isSolved = quiz.getIsSolved();
    }
}

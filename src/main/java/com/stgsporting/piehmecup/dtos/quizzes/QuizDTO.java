package com.stgsporting.piehmecup.dtos.quizzes;

import com.stgsporting.piehmecup.entities.Question;
import com.stgsporting.piehmecup.entities.Quiz;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class QuizDTO {
    private Long id;
    private String name;
    private String slug;
    private Long coins;
    private Long coinsEarned;
    private Boolean isSolved;
    private List<QuestionDTO> questions;

    public QuizDTO(Quiz quiz) {
        this.id = quiz.getId();
        this.name = quiz.getName();
        this.slug = quiz.getSlug();
        this.coins = quiz.getCoins();
        this.isSolved = quiz.getIsSolved();
        this.coinsEarned = quiz.getCoinsEarned();

        this.questions = quiz.getQuestions().stream().map(QuestionDTO::new).toList();
    }
}

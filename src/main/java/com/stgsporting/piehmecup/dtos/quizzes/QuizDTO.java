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
    private List<Question> questions;

    public QuizDTO(Quiz quiz) {
        this.id = quiz.getId();
        this.name = quiz.getName();
        this.slug = quiz.getSlug();

        this.questions = quiz.getQuestions();
    }
}

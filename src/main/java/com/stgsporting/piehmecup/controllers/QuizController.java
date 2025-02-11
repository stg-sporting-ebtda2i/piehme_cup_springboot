package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.dtos.quizzes.QuizDTO;
import com.stgsporting.piehmecup.dtos.quizzes.QuizInListDTO;
import com.stgsporting.piehmecup.entities.Quiz;
import com.stgsporting.piehmecup.services.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

    private final QuizService quizService;

    QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("")
    public ResponseEntity<Object> index() {
        return ResponseEntity.ok(
                quizService.getQuizzesForUser().stream().map(QuizInListDTO::new).toList()
        );
    }

    @GetMapping("/{slug}")
    public ResponseEntity<Object> show(@PathVariable String slug) {
        Quiz quiz = quizService.getQuizBySlug(slug);

        return ResponseEntity.ok(new QuizDTO(quiz));
    }
}

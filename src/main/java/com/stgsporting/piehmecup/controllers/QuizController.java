package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.dtos.quizzes.QuizDTO;
import com.stgsporting.piehmecup.dtos.quizzes.QuizInListDTO;
import com.stgsporting.piehmecup.entities.Quiz;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.services.QuizService;
import com.stgsporting.piehmecup.services.UserService;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

    private final QuizService quizService;
    private final UserService userService;

    QuizController(QuizService quizService, UserService userService) {
        this.quizService = quizService;
        this.userService = userService;
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

    @PostMapping("/{slug}")
    public ResponseEntity<Object> submit(@PathVariable String slug, @RequestBody JSONObject answers) {
        User user = (User) userService.getAuthenticatable();

        Quiz quiz = quizService.getQuizBySlug(slug);

        Long points = quizService.submitQuiz(user, quiz, answers);

        JSONObject response = new JSONObject();
        response.put("points", points);

        return ResponseEntity.ok(response);
    }
}

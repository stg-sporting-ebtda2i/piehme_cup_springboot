package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.entities.Quiz;
import com.stgsporting.piehmecup.entities.SchoolYear;
import com.stgsporting.piehmecup.enums.QuestionType;
import com.stgsporting.piehmecup.exceptions.NotFoundException;
import com.stgsporting.piehmecup.helpers.Response;
import com.stgsporting.piehmecup.services.AdminService;
import com.stgsporting.piehmecup.services.HttpService;
import com.stgsporting.piehmecup.services.QuizService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ostaz/quizzes")
public class AdminQuizController {

    private final QuizService quizService;
    private final AdminService adminService;
    private final HttpService httpService;

    AdminQuizController(QuizService quizService, AdminService adminService, HttpService httpService) {
        this.quizService = quizService;
        this.adminService = adminService;
        this.httpService = httpService;
    }

    @GetMapping("/upload")
    public ResponseEntity<Object> uploadUrl() {
        Response response = httpService.get("/upload/key");

        if (! response.isSuccessful()) {
            throw new NotFoundException("Upload url not found");
        }

        return ResponseEntity.ok(
                Map.of("url", response.getJsonBody().getAsString("url"))
        );
    }

    @GetMapping("")
    public ResponseEntity<Object> index() {
        List<Quiz> quizzes = quizService.getQuizzes(
                adminService.getAuthenticatable().getSchoolYear(),
                null
        );

        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<Object> show(@PathVariable String slug) {
        return ResponseEntity.ok(
                quizService.getQuizBySlug(
                        slug, adminService.getAuthenticatable().getSchoolYear(), true
        ));
    }

    private void mapQuiz(JSONObject quiz) {
        SchoolYear schoolYear = adminService.getAuthenticatable().getSchoolYear();
        quiz.put("group", schoolYear.getSlug());

        List<Map<String, Object>> questions = (List<Map<String, Object>>) quiz.get("questions");
        JSONArray newQuestions = new JSONArray();

        for (Map<String, Object> question : questions) {
            question.put("type", QuestionType.fromName((String) question.get("type")).toId());
            newQuestions.add(question);
        }
        quiz.put("questions", newQuestions);
    }

    @PostMapping("")
    public ResponseEntity<Object> store(@RequestBody JSONObject quiz) {
        mapQuiz(quiz);

        Response response = httpService.post("/quizzes", quiz);

        if (! response.isSuccessful()) {
            return ResponseEntity
                    .status(response.getStatusCode())
                    .body(response.getJsonBody());
        }

        return ResponseEntity.ok(
                Map.of("message", "Quiz Created Successfully")
        );
    }

    @PatchMapping("/{quizId}")
    public ResponseEntity<Object> update(@RequestBody JSONObject quiz, @PathVariable Long quizId) {
        mapQuiz(quiz);
        Response response = httpService.patch("/quizzes/" + quizId, quiz);

        if (! response.isSuccessful()) {
            return ResponseEntity
                    .status(response.getStatusCode())
                    .body(response.getJsonBody());
        }

        return ResponseEntity.ok(
                Map.of("message", "Quiz Updated Successfully")
        );
    }
}

package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.authentication.Authenticatable;
import com.stgsporting.piehmecup.entities.Quiz;
import com.stgsporting.piehmecup.entities.SchoolYear;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.exceptions.InvalidCredentialsException;
import com.stgsporting.piehmecup.exceptions.NotFoundException;
import com.stgsporting.piehmecup.helpers.Response;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {
    private final UserService userService;
    private final EntityService entityService;
    private final HttpService httpService;
    private final WalletService walletService;

    QuizService(UserService userService, EntityService entityService, HttpService httpService, WalletService walletService) {
        this.userService = userService;
        this.entityService = entityService;
        this.httpService = httpService;
        this.walletService = walletService;
    }

    public List<Quiz> getQuizzesForUser() {
        Authenticatable user = userService.getAuthenticatable();
        SchoolYear schoolYear = user.getSchoolYear();
        String url = "/groups/" + schoolYear.getSlug();

        Response response = httpService.get(url);

        List<Quiz> quizzes = new ArrayList<>();
        if (response.isSuccessful()) {
            JSONObject jsonObject = response.getJsonBody();
            JSONObject data = (JSONObject) jsonObject.get("group");
            JSONArray quizzesArray = (JSONArray) data.get("quizzes");

            for (Object quizObject : quizzesArray) {
                JSONObject quizJson = (JSONObject) quizObject;

                quizzes.add(Quiz.fromJson(quizJson, schoolYear));
            }
        }

        return quizzes;
    }

    public Quiz getQuizBySlug(String slug) {
        Authenticatable user = userService.getAuthenticatable();
        SchoolYear schoolYear = user.getSchoolYear();
        String url = "/quizzes/" + schoolYear.getSlug() + "/" + slug;

        Response response = httpService.get(url);

        if (response.isSuccessful()) {
            JSONObject jsonObject = response.getJsonBody();
            JSONObject data = (JSONObject) jsonObject.get("quiz");

            return Quiz.fromJson(data, schoolYear);
        }

        throw new NotFoundException("Quiz not found");
    }

    public Long submitQuiz(User user, Quiz quiz, JSONObject answers) {
        if(user.getQuizId() == null || user.getQuizId() == 0) {
            user.setQuizId(
                    entityService.createEntity(user.getUsername(), user.getSchoolYear())
            );
            userService.save(user);
        }

        StringBuilder url = new StringBuilder("/quizzes/")
                .append(user.getSchoolYear().getSlug())
                .append("/")
                .append(quiz.getSlug())
                .append("/")
                .append(user.getQuizId())
                .append("/submit");

        Response response = httpService.post(url.toString(), answers);

        if (response.isSuccessful()) {
            JSONObject jsonObject = response.getJsonBody();

            Long points = (Long) jsonObject.get("points");

            walletService.credit(user, points.intValue());

            return points;
        }

        int statusCode = response.getStatusCode().value();
        if (statusCode == 404) {
            throw new NotFoundException("Quiz not found");
        }

        if (statusCode == 400) {
            throw new InvalidCredentialsException(
                    response.getJsonBody().getAsString("message")
            );
        }

        return 0L;
    }
}

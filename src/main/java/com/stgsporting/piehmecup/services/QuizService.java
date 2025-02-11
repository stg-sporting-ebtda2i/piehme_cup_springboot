package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.authentication.Authenticatable;
import com.stgsporting.piehmecup.dtos.quizzes.QuizInListDTO;
import com.stgsporting.piehmecup.entities.Quiz;
import com.stgsporting.piehmecup.entities.SchoolYear;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.exceptions.NotFoundException;
import com.stgsporting.piehmecup.helpers.Http;
import com.stgsporting.piehmecup.helpers.Response;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {
    @Value("${quizzes.api.key}")
    private String API_KEY;

    @Value("${quizzes.api.secret}")
    private String API_SECRET;

    @Value("${quizzes.api.url}")
    private String BASE_URL;

    private final UserService userService;
    QuizService(UserService userService) {
        this.userService = userService;
    }

    public List<Quiz> getQuizzesForUser() {
        Authenticatable user = userService.getAuthenticatable();
        SchoolYear schoolYear = user.getSchoolYear();
        String url = BASE_URL + "/groups/" + schoolYear.getSlug();

        Response response = new Http(url, API_KEY, API_SECRET).get();

        List<Quiz> quizzes = new ArrayList<>();
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            JSONParser parser = new JSONParser(1);

            try {
                JSONObject jsonObject = (JSONObject) parser.parse(response.getBody());
                JSONObject data = (JSONObject) jsonObject.get("group");
                JSONArray quizzesArray = (JSONArray) data.get("quizzes");

                for (Object quizObject : quizzesArray) {
                    JSONObject quizJson = (JSONObject) quizObject;

                    quizzes.add(Quiz.fromJson(quizJson, schoolYear));
                }
            } catch (ParseException ignored) {}
        }

        return quizzes;
    }

    public Quiz getQuizBySlug(String slug) {
        Authenticatable user = userService.getAuthenticatable();
        SchoolYear schoolYear = user.getSchoolYear();
        String url = BASE_URL + "/quizzes/" + schoolYear.getSlug() + "/" + slug;

        Response response = new Http(url, API_KEY, API_SECRET).get();

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            JSONParser parser = new JSONParser(1);

            try {
                JSONObject jsonObject = (JSONObject) parser.parse(response.getBody());
                JSONObject data = (JSONObject) jsonObject.get("quiz");

                return Quiz.fromJson(data, schoolYear);
            } catch (ParseException ignored) {}
        }

        throw new NotFoundException("Quiz not found");
    }
}

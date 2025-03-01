package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.entities.SchoolYear;
import com.stgsporting.piehmecup.helpers.Response;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EntityService {

    private final HttpService httpService;

    EntityService(HttpService httpService) {
        this.httpService = httpService;
    }

    public Long createEntity(String name, SchoolYear schoolYear) {
        String url = "/entities";

        JSONObject body = new JSONObject();
        body.put("name", name);
        body.put("group", schoolYear.getSlug());

        Response response = httpService.post(url, body);

        if(!response.isSuccessful()) {
            return null;
        }

        JSONObject jsonObject = response.getJsonBody();
        JSONObject entity = (JSONObject) jsonObject.get("entity");

        return (Long) entity.get("id");
    }

    public Map<String, Long> createEntities(List<String> usernames, SchoolYear schoolYear) {
        String url = "/entities/bulk";

        JSONObject body = new JSONObject();
        JSONArray users = new JSONArray();

        for (String username : usernames) {
            JSONObject user = new JSONObject();
            user.put("name", username);
            users.add(user);
        }
        body.put("group", schoolYear.getSlug());
        body.put("entities", users);

        Response response = httpService.post(url, body);

        if(!response.isSuccessful()) {
            return null;
        }

        Map<String, Long> entities = new HashMap<>();

        JSONObject entitiesJSON = (JSONObject) response.getJsonBody().get("entities");
        for(String name : entitiesJSON.keySet()) {
            entities.put(name, (Long) entitiesJSON.getAsNumber(name));
        }

        return entities;
    }
}

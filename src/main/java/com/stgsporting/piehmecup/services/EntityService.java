package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.entities.SchoolYear;
import com.stgsporting.piehmecup.helpers.Response;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

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
}

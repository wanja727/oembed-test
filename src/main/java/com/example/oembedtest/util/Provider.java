package com.example.oembedtest.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class Provider {

    @Value("classpath:providers.json")
    Resource resource;

    public List<String> readJsonFile() throws IOException, ParseException {

        List<String> list = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new InputStreamReader(resource.getInputStream(), "UTF-8"));

        for (int i = 0; i < jsonArray.size(); i++) {

            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            JSONArray endpoints = (JSONArray) jsonObject.get("endpoints");

            /*
                https://www.edumedia-sciences.com/oembed.json
                https://graph.facebook.com/v10.0/oembed_post
                https://ndla.no/oembed
                https://api.veer.tv/oembed
                위 4개의 Provider는 endpoints가 2개 이상 존재
             */

            JSONObject endpoint = (JSONObject) endpoints.get(0);
            String url = (String) endpoint.get("url");

            list.add(url);
        }

        return list;
    }
}

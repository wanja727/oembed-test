package com.example.oembedtest.service;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.apache.tomcat.util.json.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
//import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;

@Service
@Log4j2
public class MainService {

    private final static String BASE_URL = "https://www.youtube.com/oembed?url=http%3A//youtube.com/watch%3Fv%3DM3r2XDceM6A&format=json";

    public void search(String url) throws ParseException {

        /*
        final String uri = "https://www.youtube.com/oembed?url=http%3A//youtube.com/watch%3Fv%3DM3r2XDceM6A&format=json";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                request,
                String.class
        );

        log.info(response.toString());

        String result = restTemplate.getForObject(uri, String.class);

        log.info(result);

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(result);
        JSONObject jsonObj = (JSONObject) obj;


        return jsonObj;
        */

            DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL); //UriBuilder를 생성하는 옵션을 설정하는 DefaultUriBuilderFactory 인스턴스 생성
            factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY); //encoding 모드 설정

            WebClient wc = WebClient.builder().uriBuilderFactory(factory).baseUrl(BASE_URL).build();


            String response = wc.get()
                    .uri(uriBuilder -> uriBuilder.build())
                    .retrieve().bodyToMono(String.class).block();

            System.out.println(response);
    }



}

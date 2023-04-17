package com.example.oembedtest.service;

import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Log4j2
public class MainService {

    public JSONObject search(String url) throws ParseException {

        log.info("입력받은 url : " + url);

        String BASE_URL = "";

        //TODO 도메인명 정규표현식으로 추출하기

        // Provider별로 oEmbed endpoint 설정
        if(url.contains("youtube")) {
            BASE_URL = "https://www.youtube.com/oembed";
        } else if (url.contains("vimeo")) {
            BASE_URL = "https://vimeo.com/api/oembed.json";
        } else if (url.contains("twitter")) {
            BASE_URL = "https://publish.twitter.com/oembed";
        } else if (url.contains("instagram")) {

        }

        // WebClient 방식으로 호출
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL); // UriBuilder를 생성하는 옵션을 설정하는 DefaultUriBuilderFactory 인스턴스 생성
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY); // encoding 모드 설정

        WebClient wc = WebClient.builder().uriBuilderFactory(factory).baseUrl(BASE_URL).build();

        String response = wc.get()
                .uri(uriBuilder -> uriBuilder.queryParam("url", url).build())
                .retrieve().bodyToMono(String.class).block();

        log.info("response : " + response);

        // json String -> json Object
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(response);
        JSONObject jsonObj = (JSONObject) obj;

        return jsonObj;
    }


}

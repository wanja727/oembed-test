package com.example.oembedtest.service;

import lombok.extern.log4j.Log4j2;
//import org.apache.tomcat.util.json.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
//import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Log4j2
public class MainService {

//    private final static String BASE_URL = "https://www.youtube.com/oembed?url=http%3A//youtube.com/watch%3Fv%3DM3r2XDceM6A&format=json";
//    private final static String BASE_URL = "https://www.youtube.com/oembed";

    public JSONObject search(String url) throws ParseException {

        log.info("입력받은 url : " + url);

        String BASE_URL = "";

        if(url.contains("youtube")){
            BASE_URL = "https://www.youtube.com/oembed";
        } else if (url.contains("vimeo")) {
            BASE_URL = "https://vimeo.com/api/oembed.json";
        } else if (url.contains("twitter")) {
            BASE_URL = "https://publish.twitter.com/oembed";
        }

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
                .uri(uriBuilder -> uriBuilder.queryParam("url", url).build())
                .retrieve().bodyToMono(String.class).block();

        log.info(response);

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(response);
        JSONObject jsonObj = (JSONObject) obj;

        return jsonObj;
    }


}

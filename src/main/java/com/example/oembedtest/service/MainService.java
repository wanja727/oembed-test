package com.example.oembedtest.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Log4j2
public class MainService {

    public String search(String url) {

        log.info("입력받은 url : " + url);

        String BASE_URL = "";

        // 도메인명 추출 정규표현식
        String domain = "";
        String regex = "(?:https?:\\/\\/)?(?:www\\.)?([^\\.]+)\\.";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            domain = matcher.group(1);
            log.info("url에서 추출한 도메인명 : " + domain);
        }else {
            domain = "";
        }

        // Provider별로 oEmbed endpoint 설정
        if (domain.contains("youtube")) {
            BASE_URL = "https://www.youtube.com/oembed";
        } else if (domain.contains("vimeo")) {
            BASE_URL = "https://vimeo.com/api/oembed.json";
        } else if (domain.contains("twitter")) {
            BASE_URL = "https://publish.twitter.com/oembed";
        } else if (domain.contains("instagram")) {
            throw new RuntimeException("인스타그램,페이스북은 정책상 oEmbed사용을 금지하고 있습니다.");
        } else {
            throw new RuntimeException("현재 지원하지 않는 Provider 입니다.");
        }

        // WebClient 방식으로 호출
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL); // UriBuilder를 생성하는 옵션을 설정하는 DefaultUriBuilderFactory 인스턴스 생성
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY); // encoding 모드 설정
        WebClient wc = WebClient.builder().uriBuilderFactory(factory).baseUrl(BASE_URL).build();

        String response = wc.get()
                .uri(uriBuilder -> uriBuilder.queryParam("url", url).build())
                .retrieve().bodyToMono(String.class).block();

//        ResponseEntity<String> response = wc.get()
//                .uri(uriBuilder -> uriBuilder.queryParam("url", url).build())
//                .retrieve()
//                .onStatus(HttpStatus::isError, clientResponse -> Mono.error(new Exception("에러 메세지"))).toEntity(String.class).block();

        log.info("response : " + response);

        // json String -> json Object
//        JSONParser parser = new JSONParser();
//        Object obj = parser.parse(response);
//        JSONObject jsonObj = (JSONObject) obj;

        return response;
    }


}

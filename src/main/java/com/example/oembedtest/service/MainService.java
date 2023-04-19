package com.example.oembedtest.service;

import com.example.oembedtest.util.Provider;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Log4j2
@RequiredArgsConstructor
public class MainService {

    private final Provider provider;

    public String search(String url) throws IOException, ParseException {

        // Provider.json 파일에서 읽어온 url정보
        List<String> list = provider.readJsonFile();

        log.info("입력받은 url : " + url);

        String BASE_URL = "";

        // 도메인명 추출 정규표현식
        String domain = "";
        //String regex = "(?:https?:\\/\\/)?(?:www\\.)?([^\\.]+)\\.";
        String regex = "^(?:https?:\\/\\/)?(?:www\\.)?([^\\/]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            domain = matcher.group(1);
            log.info("url에서 추출한 도메인명 : " + domain);
        } else {
            throw new RuntimeException("입력된 url에서 도메인명을 추출하지 못했습니다. url을 확인해주세요.");
        }

        if (domain.contains("instagram") || domain.contains("facebook")) {
            throw new RuntimeException("인스타그램, 페이스북은 정책상 oEmbed사용을 금지하고 있습니다.");
        }

        // 도메인명에 해당하는 oEmbed url 찾기
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains(domain)) {
                String str = list.get(i);
                str = str.replace("http:", "https:");
                str = str.replace("{format}", "json");
                BASE_URL = str;
            }
        }

        //log.info("BASE_URL : " + BASE_URL);

        if (BASE_URL.isBlank()) {
            throw new RuntimeException("현재 지원하지 않는 Provider 입니다.");
        }

        // WebClient 방식으로 호출
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL); // UriBuilder를 생성하는 옵션을 설정하는 DefaultUriBuilderFactory 인스턴스 생성
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY); // encoding 모드 설정
        WebClient wc = WebClient.builder().uriBuilderFactory(factory).baseUrl(BASE_URL).build();

        String response = wc.get()
                .uri(uriBuilder -> uriBuilder.queryParam("url", url).queryParam("format", "json").build())
                .retrieve().bodyToMono(String.class).block();

        //log.info("response : " + response);

        return response;
    }


}

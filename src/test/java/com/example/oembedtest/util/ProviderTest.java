package com.example.oembedtest.util;

import com.example.oembedtest.service.MainService;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class ProviderTest {

    @Autowired
    Provider provider;

    @Test
    @DisplayName("oembed api 호출")
    void readFile() throws IOException, ParseException {
        List<String> list = provider.readJsonFile();
        list.forEach(System.out::println);
    }

}
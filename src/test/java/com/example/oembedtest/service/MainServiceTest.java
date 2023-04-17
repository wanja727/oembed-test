package com.example.oembedtest.service;

import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class MainServiceTest {

    @Autowired
    MainService mainService;

    @Test
    @DisplayName("oembed api 호출")
    void search() throws ParseException {

//        ResponseEntity<String> jsonObject = mainService.search("https://www.youtube.com/watch?v=dBD54EZIrZo");
//        log.info(jsonObject);
    }

}
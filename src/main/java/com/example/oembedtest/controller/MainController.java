package com.example.oembedtest.controller;

import com.example.oembedtest.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping
@Log4j2
public class MainController {

    private final MainService mainService;

    @GetMapping("/main")
    public String main(Model model) {
        return "main";
    }

    @GetMapping("/search")
    @ResponseBody
    public JSONObject search(HttpServletRequest request, Model model) throws IOException, ParseException {
        String url = request.getParameter("url");
        return mainService.search(url);
    }

}

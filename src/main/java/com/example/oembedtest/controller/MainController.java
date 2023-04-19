package com.example.oembedtest.controller;

import com.example.oembedtest.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping
@Log4j2
public class MainController {

    private final MainService mainService;

    @GetMapping("/main")
    public String main(Model model) {
        // providers.json 파일 읽기


        return "main";
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity search(HttpServletRequest request, Model model) {

        String url = request.getParameter("url");
        String result = "";
        String errMsg = "";

        try{
            result = mainService.search(url);

        } catch(WebClientResponseException e) {
            if(e.getStatusCode() == HttpStatus.BAD_REQUEST){
                errMsg = "해당 url로 oEmbed 요청시 에러가 발생했습니다. 주소를 확인해주세요.\n"+url;
                return new ResponseEntity<String>(errMsg, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<String>(e.getMessage(), e.getStatusCode());

        } catch (RuntimeException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

}

package com.microservice.quiz_service.feign;

import com.microservice.quiz_service.model.QuestionWrapper;
import com.microservice.quiz_service.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface FeignInterface {
    @PostMapping("question/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz
            (@RequestParam String category, @RequestParam Integer numQ, @RequestParam String diffLevel);

    @PostMapping("question/getquestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsById(@RequestBody List<Integer> ids);

    @PostMapping("question/getscore")
    public ResponseEntity<Integer> calculateScore(@RequestBody List<Response> response);
}

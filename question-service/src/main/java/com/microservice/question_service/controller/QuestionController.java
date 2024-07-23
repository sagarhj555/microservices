package com.microservice.question_service.controller;

import com.microservice.question_service.model.Question;
import com.microservice.question_service.model.QuestionWrapper;
import com.microservice.question_service.model.Response;
import com.microservice.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired QuestionService questionService;

    @GetMapping("all")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @PostMapping("add")
    public ResponseEntity<String> saveQuestion(@RequestBody Question question) {
        return questionService.saveQuestion(question);
    }

    @GetMapping("category/{key}")
    public ResponseEntity<List<Question>> getQuestionsOnCategory(@PathVariable String key) {
        return questionService.getQuestionsOnCategory(key);
    }

    @PutMapping("update")
    public ResponseEntity<String> updateQuestions(@RequestBody Question question) {
        return questionService.updateQuestions(question);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id) {
        return questionService.deleteQuestion(id);
    }

    @PostMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz
            (@RequestParam String category, @RequestParam Integer numQ, @RequestParam String diffLevel) {
        return questionService.getQuestionsIdsForQuiz(category, numQ, diffLevel);
    }

    @PostMapping("getquestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsById(@RequestBody List<Integer> ids) {
        return questionService.getQuestionsById(ids);
    }

    @PostMapping("getscore")
    public ResponseEntity<Integer> calculateScore(@RequestBody List<Response> response) {
        return questionService.calculateScore(response);
    }
}

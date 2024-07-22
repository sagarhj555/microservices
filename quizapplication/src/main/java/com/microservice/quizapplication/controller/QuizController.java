package com.microservice.quizapplication.controller;

import com.microservice.quizapplication.model.QuestionWrapper;
import com.microservice.quizapplication.model.Response;
import com.microservice.quizapplication.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(
            @RequestParam String category, @RequestParam int numQ,
            @RequestParam String diffLevel, @RequestParam String title) {
        return quizService.createQuiz(category, numQ, diffLevel, title);
    }

    @GetMapping("get/{quizId}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer quizId) {
        return quizService.getQuizQuestions(quizId);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> response) {
        return quizService.calculateScore(id, response);
    }
}
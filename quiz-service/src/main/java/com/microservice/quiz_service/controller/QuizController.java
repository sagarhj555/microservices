package com.microservice.quiz_service.controller;

import com.microservice.quiz_service.model.QuestionWrapper;
import com.microservice.quiz_service.model.QuizDto;
import com.microservice.quiz_service.model.Response;
import com.microservice.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<Integer> createQuiz(@RequestBody QuizDto quizDto) {
        return quizService.createQuiz(quizDto.getCategory(), quizDto.getNumQ(), quizDto.getDiffLevel(), quizDto.getTitle());
    }

    @GetMapping("get/{quizId}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer quizId) {
        return quizService.getQuizQuestions(quizId);
    }

    @PostMapping("submit")
    public ResponseEntity<Integer> submitQuiz(@RequestBody List<Response> response) {
        return quizService.calculateScore(response);
    }
}

package com.microservice.quizapplication.controller;

import com.microservice.quizapplication.model.Question;
import com.microservice.quizapplication.service.QuestionService;
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
}

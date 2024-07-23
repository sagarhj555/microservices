package com.microservice.quizapplication.controller;

import com.microservice.quizapplication.dao.QuestionRepo;
import com.microservice.quizapplication.model.Question;
import com.microservice.quizapplication.model.QuestionWrapper;
import com.microservice.quizapplication.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired QuestionRepo questionRepo;

    @PostMapping("create")
    public ResponseEntity<List<QuestionWrapper>> createTest(@RequestParam String category, @RequestParam Integer numQ, @RequestParam String diffLevel) {
        List<Question> questionsFromDb = questionRepo.createQuizByRandom(category, numQ, diffLevel);
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for (Question q : questionsFromDb) {
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(),
                    q.getOption1(), q.getOption2(), q.getOption3(),
                    q.getOption4());
            questionsForUser.add(qw);
        }
        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    @PostMapping("getscore")
    public ResponseEntity<Integer> calculateScore(@RequestBody List<Response> responses) {
        int score = 0;
        for (Response q : responses) {
            Question quizId = questionRepo.findById(q.getId()).get();
            if (quizId.getRightAnswer().equals(q.getResponse()))
                score++;
        }
        return new ResponseEntity<>(score, HttpStatus.OK);
    }
}

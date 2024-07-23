package com.microservice.question_service.controller;

import com.microservice.question_service.dao.QuestionRepo;
import com.microservice.question_service.model.Question;
import com.microservice.question_service.model.QuestionWrapper;
import com.microservice.question_service.model.Response;
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
        List<Question> quizByRandom = questionRepo.createQuizByRandom(category, numQ, diffLevel);
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        int i = 0;
        for (Question q : quizByRandom) {
            Question questionsFromDb = quizByRandom.get(i);
            QuestionWrapper qw = new QuestionWrapper(questionsFromDb.getId(), questionsFromDb.getQuestionTitle(),
                    questionsFromDb.getOption1(), questionsFromDb.getOption2(), questionsFromDb.getOption3(),
                    questionsFromDb.getOption4());
            i++;
            questionsForUser.add(qw);
        }
        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    @PostMapping("getscore")
    public ResponseEntity<Integer> calculateScore(@RequestBody List<Response> responses) {
        int i = 0, score = 0;
        for (Response q : responses) {
            Response responseFromUser = responses.get(i);
            Optional<Question> quizId = questionRepo.findById(responseFromUser.getId());
            if (quizId.get().getRightAnswer().equals(responseFromUser.getResponse())) {
                score++;
            }
            i++;
        }
        return new ResponseEntity<>(score, HttpStatus.OK);
    }
}

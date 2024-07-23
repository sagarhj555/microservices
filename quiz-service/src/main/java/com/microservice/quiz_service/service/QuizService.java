package com.microservice.quiz_service.service;

import com.microservice.quiz_service.dao.QuizRepo;
import com.microservice.quiz_service.feign.FeignInterface;
import com.microservice.quiz_service.model.QuestionWrapper;
import com.microservice.quiz_service.model.Quiz;
import com.microservice.quiz_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    @Autowired QuizRepo quizRepo;
    @Autowired FeignInterface feignInterface;

    public ResponseEntity<Integer> createQuiz(String category, int numQ, String diffLevel, String title) {
        try {
            List<Integer> questionsForQuiz = feignInterface.getQuestionsForQuiz(category, numQ, diffLevel).getBody();
            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestionIds(questionsForQuiz);
            quizRepo.save(quiz);
            return new ResponseEntity<>(quiz.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(-1, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer quizId) {
        try {
            Quiz quiz = quizRepo.findById(quizId).get();
            List<Integer> questionIdsFromDb = quiz.getQuestionIds();
            ResponseEntity<List<QuestionWrapper>> questionsForUser = feignInterface.getQuestionsById(questionIdsFromDb);
            return questionsForUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Integer> calculateScore(List<Response> response) {
        return feignInterface.calculateScore(response);
    }
}

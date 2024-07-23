package com.microservice.question_service.service;

import com.microservice.question_service.dao.QuestionRepo;
import com.microservice.question_service.model.Question;
import com.microservice.question_service.model.QuestionWrapper;
import com.microservice.question_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired QuestionRepo questionRepo;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionRepo.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> saveQuestion(Question question) {
        try {
            questionRepo.save(question);
            return new ResponseEntity<>("ADDED", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("ERROR WHILE ADDING", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsOnCategory(String key) {
        try {
            return new ResponseEntity<>(questionRepo.findByCategory(key), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> updateQuestions(Question question) {
        try {
            questionRepo.save(question);
            return new ResponseEntity<>("UPDATED", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("ERROR WHILE UPDATING", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        try {
            questionRepo.deleteById(id);
            return new ResponseEntity<>("DELETED", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("ERROR WHILE DELETING", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<Integer>> getQuestionsIdsForQuiz(String category, Integer numQ, String diffLevel) {
        List<Integer> quizByRandom = questionRepo.getQuestionsIdsForQuiz(category, numQ, diffLevel);
        return new ResponseEntity<>(quizByRandom, HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsById(List<Integer> ids) {
        List<QuestionWrapper> wrapper = new ArrayList<>();
        List<Question> allById = questionRepo.findAllById(ids);
        for (Question q : allById) {
            QuestionWrapper qw = new QuestionWrapper();
            qw.setId(q.getId());
            qw.setQuestionTitle(q.getQuestionTitle());
            qw.setOption1(q.getOption1());
            qw.setOption2(q.getOption2());
            qw.setOption3(q.getOption3());
            qw.setOption4(q.getOption4());
            wrapper.add(qw);
        }
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateScore(List<Response> response) {
        int score = 0;
        for (Response rs : response) {
            Question qId = questionRepo.findById(rs.getId()).get();
            if (rs.getResponse().equals(qId.getRightAnswer()))
                score++;
        }
        return new ResponseEntity<>(score, HttpStatus.OK);
    }
}

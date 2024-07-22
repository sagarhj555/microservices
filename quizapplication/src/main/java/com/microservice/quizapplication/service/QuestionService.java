package com.microservice.quizapplication.service;

import com.microservice.quizapplication.dao.QuestionRepo;
import com.microservice.quizapplication.model.Question;
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
}

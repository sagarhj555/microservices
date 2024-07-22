package com.microservice.quizapplication.service;

import com.microservice.quizapplication.dao.QuestionRepo;
import com.microservice.quizapplication.dao.QuizRepo;
import com.microservice.quizapplication.model.Question;
import com.microservice.quizapplication.model.QuestionWrapper;
import com.microservice.quizapplication.model.Quiz;
import com.microservice.quizapplication.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired QuestionRepo questionRepo;
    @Autowired QuizRepo quizRepo;

    public ResponseEntity<String> createQuiz(String category, int numQ, String diffLevel, String title) {
        try {
            List<Question> questionsFromDb = questionRepo.createQuizByRandom(category, numQ, diffLevel);
            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questionsFromDb);
            quizRepo.save(quiz);
            return new ResponseEntity<>("QUIZ CREATED", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("ERROR WHILE CREATING QUIZ", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer quizId) {
        try {
            Optional<Quiz> quiz = quizRepo.findById(quizId);
            List<Question> questionsFromDb = quiz.get().getQuestions();
            List<QuestionWrapper> questionsForUser = new ArrayList<>();
            for (Question q : questionsFromDb) {
                QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
                questionsForUser.add(qw);
            }
            return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Integer> calculateScore(Integer id, List<Response> response) {
        Optional<Quiz> quiz = quizRepo.findById(id);
        List<Question> questions = quiz.get().getQuestions();
        int score = 0, i = 0;
        for (Response rs : response) {
            if (rs.getResponse().equals(questions.get(i).getRightAnswer()))
                score++;
            i++;
        }
        return new ResponseEntity<>(score, HttpStatus.OK);
    }
}

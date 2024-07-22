package com.microservice.quizapplication.dao;

import com.microservice.quizapplication.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {
    List<Question> findByCategory(String key);

    @Query(value = "SELECT * FROM questions q WHERE q.category=:category " +
            "AND q.difficulty_level=:diffLevel ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
    List<Question> createQuizByRandom(String category, int numQ, String diffLevel);

}

package com.microservice.quiz_service.model;

import lombok.Data;

@Data
public class QuizDto {
    private String category;
    private Integer numQ;
    private String diffLevel;
    private String title;
}

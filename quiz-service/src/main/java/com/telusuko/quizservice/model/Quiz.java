package com.telusuko.quizservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.aspectj.weaver.patterns.TypePatternQuestions;

import java.util.List;

@Data
@Entity
public class Quiz {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;

    @ElementCollection
    private List<Integer> questions;

}

package com.telusuko.questionservice.controller;


import com.telusuko.questionservice.model.Question;
import com.telusuko.questionservice.model.QuestionWrapper;
import com.telusuko.questionservice.model.Response;
import com.telusuko.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    Environment environment;


    @RequestMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        System.out.println("port userd: "+environment.getProperty("local.server.port"));
        return questionService.getAllQuestions();
    }

    @RequestMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }
    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> addQuestion(@PathVariable int id) {
        return questionService.deleteQuestion(id);
    }

    //generate random questions for quiz
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz
        (@RequestParam String categoryName, @RequestParam int numQ) {
        System.out.println("port userd: "+environment.getProperty("local.server.port"));

        return questionService.getQuestionsForQuiz(categoryName,numQ);
    }
    //get question by id
    @PostMapping("getQuestionsById")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds) {
        System.out.println("port userd: "+environment.getProperty("local.server.port"));

        return questionService.getQuestionsFromId(questionIds);
    }
    //getscore for quiz

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) {
        return questionService.getScore(responses);
    }

}

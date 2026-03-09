package com.telusuko.quizapp.controller;

import com.telusuko.quizapp.model.Question;
import com.telusuko.quizapp.model.QuestionWrapper;
import com.telusuko.quizapp.model.Response;
import com.telusuko.quizapp.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }
//http://localhost:8080/quiz/create?catagory=Java&numQ=5&title=JavaQuiz
    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestParam String catagory, @RequestParam int numQ ,@RequestParam String title) {
        return quizService.createQuiz(catagory,numQ,title);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id) {
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<String> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses) {
        return quizService.caliculatResult(id,responses);

    }
}

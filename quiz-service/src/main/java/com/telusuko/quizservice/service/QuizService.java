package com.telusuko.quizservice.service;



import com.telusuko.quizservice.dao.QuizDao;
import com.telusuko.quizservice.feign.QuizInterface;
import com.telusuko.quizservice.model.QuestionWrapper;
import com.telusuko.quizservice.model.Quiz;
import com.telusuko.quizservice.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    QuizDao quizDao;
    QuizInterface quizInterface;

    public QuizService(QuizDao quizDao, QuizInterface quizInterface) {
        this.quizDao = quizDao;
        this.quizInterface = quizInterface;
    }

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Integer> questions= quizInterface.getQuestionsForQuiz(category,numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Quiz created successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz = quizDao.findById(id).get();
        List<Integer> questionIds =quiz.getQuestions();
        ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromId(questionIds);

        return questions;

    }

    public ResponseEntity<Integer> caliculatResult(Integer id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }
}

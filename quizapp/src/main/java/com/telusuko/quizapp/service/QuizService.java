package com.telusuko.quizapp.service;


import com.telusuko.quizapp.dao.QuestionDao;
import com.telusuko.quizapp.dao.QuizDao;
import com.telusuko.quizapp.model.Question;
import com.telusuko.quizapp.model.QuestionWrapper;
import com.telusuko.quizapp.model.Quiz;
import com.telusuko.quizapp.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    QuizDao quizDao;
    QuestionDao questionDao;

    public QuizService(QuestionDao questionDao, QuizDao quizDao) {
        this.questionDao = questionDao;
        this.quizDao = quizDao;
    }

    public ResponseEntity<String> createQuiz(String catagory, int numQ, String title) {
        List<Question> questions = questionDao.findRandomQuestionsByCategory(catagory,numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Quiz created successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionFromDB =quiz.get().getQuestions();

        List<QuestionWrapper> questionForUser = new ArrayList<>();
        for (Question q : questionFromDB) {
            QuestionWrapper questionWrapper = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionForUser.add(questionWrapper);
        }
        return new ResponseEntity<>(questionForUser, HttpStatus.OK);

    }

    public ResponseEntity<String> caliculatResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int right=0;
        int i=0;
        for (Response response : responses) {
            if(response.getResponse().equals(questions.get(i).getRightAnswer())){
                right++;
            }
            i++;
        }
        return new ResponseEntity<>(right+"",HttpStatus.OK);
    }
}

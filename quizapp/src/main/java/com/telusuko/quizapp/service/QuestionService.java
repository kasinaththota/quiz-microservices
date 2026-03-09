package com.telusuko.quizapp.service;

import com.telusuko.quizapp.dao.QuestionDao;
import com.telusuko.quizapp.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    QuestionDao questionDao;

    public QuestionService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);

    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try{
            return new ResponseEntity<>(questionDao.findAllByCategory(category),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(questionDao.findAllByCategory(category),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
         questionDao.save(question);
         return new ResponseEntity<>("Successfully added question",HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteQuestion(int id) {
        if (questionDao.existsById(id)) {
            questionDao.deleteById(id);
            return new ResponseEntity<>("Successfully deleted question",HttpStatus.OK);
        }
        return new ResponseEntity<>("Question not found",HttpStatus.NOT_FOUND);

    }
}

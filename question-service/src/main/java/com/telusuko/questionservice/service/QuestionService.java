package com.telusuko.questionservice.service;


import com.telusuko.questionservice.dao.QuestionDao;
import com.telusuko.questionservice.model.Question;
import com.telusuko.questionservice.model.QuestionWrapper;
import com.telusuko.questionservice.model.Response;
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

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String catogoryName, int numQ) {
        List<Integer> questions = questionDao.findRandomQuestionsByCategory(catogoryName,numQ);
        return  new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions=new ArrayList<>();
        for (Integer id : questionIds) {
            questions.add(questionDao.findById(id).get());
        }
        for (Question q : questions) {
            QuestionWrapper questionWrapper = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            wrappers.add(questionWrapper);
        }
        return new ResponseEntity<>(wrappers,HttpStatus.OK);

    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int right=0;
        for (Response response : responses) {
            Question question = questionDao.findById(response.getId()).get();
            if(response.getResponse().equals(question.getRightAnswer())){
                right++;
            }
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}

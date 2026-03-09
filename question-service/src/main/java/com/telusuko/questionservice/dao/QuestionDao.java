package com.telusuko.questionservice.dao;


import com.telusuko.questionservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {
    List<Question> findAllByCategory(String category);

    @Query(value = "SELECT q.id FROM question q WHERE q.category = ?1 ORDER BY RANDOM() LIMIT ?2", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String catagory, int numQ);
}

package com.softcell.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.softcell.model.Question;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

	List<Question> findByCategory(String category);

//	@Query(value = "select * from question q where q.category = :category order by Random() LIMIT :numQ", nativeQuery = true)
//	List<Question> findQuestionsByCategory(String category, int numQ);

	@Query(value = "SELECT * FROM question q WHERE q.category = :category ORDER BY RAND() LIMIT :numQ", nativeQuery = true)
	List<Question> findQuestionsByCategory(String category, int numQ);

}

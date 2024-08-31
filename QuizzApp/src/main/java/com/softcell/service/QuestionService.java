package com.softcell.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.softcell.dao.QuestionDao;
import com.softcell.model.Question;

@Service
public class QuestionService {

	private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

	@Autowired
	QuestionDao questionDao;

	public ResponseEntity<List<Question>> getAllQuestions() {
		try {
			return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error retrieving all questions", e);
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
		try {
			return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error retrieving question by category", e);
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<String> addQuestion(Question question) {
		try {
			questionDao.save(question);
			return new ResponseEntity<>("Success", HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Error adding question", e);
			return new ResponseEntity<>("Error Question Not Added", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public ResponseEntity<String> deleteById(int id) {
		try {
			if (!questionDao.existsById(id))
				return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);

			questionDao.deleteById(id);
			return new ResponseEntity<>("Data Deleted", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			logger.error("Question not found", e);
			return new ResponseEntity<>("Can't delete", HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<String> deleteAllData() {
		try {
			questionDao.deleteAll();
			return new ResponseEntity<>("Deleted All Records", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			logger.error("Error deleting all questions", e);
			return new ResponseEntity<>("Error: Not Authorized", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Question> questionfindById(int id) {
		Question que = questionById(id);
		if (que == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(que, HttpStatus.FOUND);
	}

	private Question questionById(int id) {
		try {
			Optional<Question> ques = questionDao.findById(id);

			if (ques.isPresent())
				return ques.get();
			else
				return null;

		} catch (Exception e) {
			logger.error("Question not found", e);
			return null;
		}
	}

	public ResponseEntity<Question> updateById(int id, Question question) {
		try {
			Question que = questionById(id);
			if (que == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);

			updateQuestionFields(que, question);
			questionDao.save(que);
			return new ResponseEntity<>(que, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error updating question", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private void updateQuestionFields(Question que, Question question) {

		if (question.getCategory() != null)
			que.setCategory(question.getCategory());
		if (question.getDifficultyLevel() != null)
			que.setDifficultyLevel(question.getDifficultyLevel());
		if (question.getOption1() != null)
			que.setOption1(question.getOption1());
		if (question.getOption2() != null)
			que.setOption2(question.getOption2());
		if (question.getOption3() != null)
			que.setOption3(question.getOption3());
		if (question.getOption4() != null)
			que.setOption4(question.getOption4());
		if (question.getQuestionTitle() != null)
			que.setQuestionTitle(question.getQuestionTitle());
		if (question.getRightAnswer() != null)
			que.setRightAnswer(question.getRightAnswer());
	}

}
